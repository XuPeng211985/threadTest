package com.poolxp.invokeany;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class UserValidator {
    private String name;
    public UserValidator(String name) {
        this.name = name;
    }
    // 验证
    public boolean validate(String name, String password) {
        Random random = new Random();
        try {
            // 睡眠一段时间, 模拟验证操作
            Long duration = (long) (Math.random() * 10);
            System.out.printf("Validator %s: Validating a user during %d seconds\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            return false;
        }
        // 随机的boolean值
        return random.nextBoolean();
    }
    public String getName() {
        return name;
    }
}
