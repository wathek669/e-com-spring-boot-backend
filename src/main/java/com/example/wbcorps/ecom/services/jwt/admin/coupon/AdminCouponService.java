package com.example.wbcorps.ecom.services.jwt.admin.coupon;

import com.example.wbcorps.ecom.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
