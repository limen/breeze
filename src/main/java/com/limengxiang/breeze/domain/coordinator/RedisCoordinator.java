package com.limengxiang.breeze.domain.coordinator;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.coordinator.ICoordinator;
import com.limengxiang.breeze.domain.job.JobPrelude;
import com.limengxiang.breeze.manager.ManagerPrelude;
import com.limengxiang.breeze.model.dao.RedisOps;
import com.limengxiang.breeze.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
/*
-- KEYS[1] lock
-- KEYS[2] tick
-- KEYS[3] job queue
-- ARGV[1] lock value
-- ARGV[2] lock ttl
-- ARGV[3] client Tick
-- ARGV[4] job queue length threshold
-- 每个tick为一秒
-- 如果任务队列积压，如队列长度大于10000，则停止发布任务
-- 每轮协调最多发布10个tick的任务
if redis.call('LLEN', KEYS[3]) > tonumber(ARGV[4]) then
    return {0,0,0}
end;
local s = redis.call('SET',KEYS[1], ARGV[1], 'PX', ARGV[2], 'NX');
local tick = 0;
local tickTo = 0;
local clientTick = tonumber(ARGV[3]);
if s ~= false then
    tick = redis.call('INCR', KEYS[2]);
    if tick == 1 then
        redis.call('SET', KEYS[2], clientTick);
        tick = clientTick;
    elseif tick < clientTick then
        if tick + 10 > clientTick then
            redis.call('SET', KEYS[2], clientTick);
            tickTo = clientTick;
        else
            redis.call('SET', KEYS[2], tick + 10);
            tickTo = tick + 10;
        end
    end
end
if tickTo == 0 then
    tickTo = tick;
end
if s == false then
    s = 0;
else
    s = 1;
end
return {s,tick, tickTo};
 */
public class RedisCoordinator implements ICoordinator {

    private Config config;

    public RedisCoordinator(Config config) {
        this.config = config;
    }

    private static final String lua =
            "if redis.call('LLEN', KEYS[3]) > tonumber(ARGV[4]) then\n" +
                    "    return {0,0,0}\n" +
                    "end;\n" +
                    "local s = redis.call('SET',KEYS[1], ARGV[1], 'PX', ARGV[2], 'NX');\n" +
                    "local tick = 0;\n" +
                    "local tickTo = 0;\n" +
                    "local clientTick = tonumber(ARGV[3]);\n" +
                    "if s ~= false then\n" +
                    "    tick = redis.call('INCR', KEYS[2]);\n" +
                    "    if tick == 1 then\n" +
                    "        redis.call('SET', KEYS[2], clientTick);\n" +
                    "        tick = clientTick;\n" +
                    "    elseif tick < clientTick then\n" +
                    "        if tick + 10 > clientTick then\n" +
                    "            redis.call('SET', KEYS[2], clientTick);\n" +
                    "            tickTo = clientTick;\n" +
                    "        else\n" +
                    "            redis.call('SET', KEYS[2], tick + 10);\n" +
                    "            tickTo = tick + 10;\n" +
                    "        end\n" +
                    "    end\n" +
                    "end\n" +
                    "if tickTo == 0 then\n" +
                    "    tickTo = tick;\n" +
                    "end\n" +
                    "if s == false then\n" +
                    "    s = 0;\n" +
                    "else\n" +
                    "    s = 1;\n" +
                    "end\n" +
                    "return {s,tick, tickTo};"
            ;

    @Autowired
    private RedisOps redisOps;

    public DutyInfo acquireDuty() {
        List resp = redisOps.execute(
                lua,
                List.class,
                Arrays.asList(ManagerPrelude.COO_LOCK_KEY, ManagerPrelude.COO_TICK_KEY, JobPrelude.JOB_QUEUE_KEY),
                config.getInstanceId(),
                1000,
                DateUtil.toTimestamp(new Date()),
                config.getJobQueueBusySize()
        );
        DutyInfo dutyInfo = new DutyInfo();
        if (resp != null) {
            dutyInfo.setOn(1L == (long) resp.get(0));
            dutyInfo.setTickFrom((long) resp.get(1));
            dutyInfo.setTickTo((long) resp.get(2));
        }
        return dutyInfo;
    }
}
