package strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * @Author : 貔貅
 * @Description :支付成功策略
 * @Date : 2024/4/18 18:50
 * version :1.0
 **/
@Component
public class PaySuccessStrategy implements PayCallbackStrategy {
    @Override
    public void handlePayCallback(CommandPay commandPay) {
        // 处理支付成功逻辑
        // 调用领域服务处理订单支付成功
        //orderDomainService.orderPaySuccess(commandPayDTO);
    }
}
