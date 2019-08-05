package com.han.util;

import com.han.entity.Customer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/5
 */
public class Painter {
    public static BufferedImage paintCustomers(List<Customer> customerList, int width) {

        int initXc = 20;

        int x = initXc;
        int y = 30;
        int xInterval = (width - 40) / 8;
        int yInterval = y;

        int height = (customerList.size() + 1) * yInterval + 10;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        Font font = new Font("宋体", Font.PLAIN, 18);
        graphics.setFont(font);

        graphics.setColor(Color.BLACK);
        graphics.drawString("编号", x += xInterval, y);
        graphics.drawString("机构代码", x += xInterval, y);
        graphics.drawString("机构名称", x += xInterval, y);
        graphics.drawString("用户总数", x += xInterval, y);
        graphics.drawString("成功总数", x += xInterval, y);
        graphics.drawString("失败总数", x += xInterval, y);
        graphics.drawString("成功率", x + xInterval, y);
        x = initXc;
        y += yInterval;
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            if (i % 2 == 0) {
                graphics.setColor(new Color(245, 245, 245));
                graphics.fillRect(x, y - 20, width, yInterval);
                graphics.setColor(Color.BLACK);
            }

            graphics.drawString(String.valueOf(i + 1), x += xInterval, y);
            graphics.drawString(customer.getCode(), x += xInterval, y);
            graphics.drawString(customer.getName(), x += xInterval, y);
            graphics.drawString(String.valueOf(customer.getTotalCount()), x += xInterval, y);
            graphics.drawString(String.valueOf(customer.getSuccessCount()), x += xInterval, y);
            graphics.drawString(String.valueOf(customer.getFailureCount()), x += xInterval, y);
            graphics.drawString(customer.getSuccessRate(), x + xInterval, y);
            x = initXc;
            y += yInterval;
        }
        graphics.dispose();
        return bufferedImage;
    }
}
