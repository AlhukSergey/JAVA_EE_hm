package by.teachmeskills.client;

import by.teachmeskills.ps.service.MerchantService;

public class Run {
    public static void main(String[] args) {
        AppMenu.start(new MerchantService());
    }
}