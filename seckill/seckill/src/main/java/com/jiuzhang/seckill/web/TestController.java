package com.jiuzhang.seckill.web;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("hello")
    public String hello() {
        String result;
        try (Entry entry = SphU.entry("HelloResource")) {
            result = "Hello Sentinel";
            return result;

        } catch (BlockException e) {
            log.error(e.toString());
            result = "busy system";
            return result;
        }
    }

    /**
     * 定义限流规则
     * 1.创建存放限流规则的集合
     * 2.创建限流规则
     * 3.将限流规则放到集合中
     * 4.加载限流规则
     * @PostConstruct 当前类的构造函数执行完之后执行
     */

    @PostConstruct
    public void seckillsFlow(){
        //1.创建存放限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        //2.创建限流规则
        FlowRule rule = new FlowRule();
        //定义资源，表示sentinel会对那个资源生效
        rule.setResource("seckills");
        //定义限流规则类型,QPS类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //定义QPS每秒通过的请求数
        rule.setCount(1);

        FlowRule rule2 = new FlowRule();
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(2);
        rule2.setResource("HelloResource");
        //3.将限流规则放到集合中
        rules.add(rule);
        rules.add(rule2);
        //4.加载限流规则
        FlowRuleManager.loadRules(rules);
    }
}
