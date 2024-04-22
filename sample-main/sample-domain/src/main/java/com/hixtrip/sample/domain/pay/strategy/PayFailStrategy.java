package strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * @Author : 貔貅
 * @Description :支付失败策略
 * @Date : 2024/4/18 21:30
 * version :1.0
 **/
@Component
public class PayFailStrategy implements PayCallbackStrategy {
    @Override
    public void handlePayCallback(CommandPay commandPay) {
        // 处理支付失败逻辑
        // 调用领域服务处理订单支付失败
        //orderDomainService.orderPayFail(commandPayDTO);
    }
}
