package com.example.wbcorps.ecom.services.jwt.admin.coupon;

import com.example.wbcorps.ecom.Exceptions.ValidationException;
import com.example.wbcorps.ecom.dto.CouponDto;
import com.example.wbcorps.ecom.entity.Coupon;
import com.example.wbcorps.ecom.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {
    private final CouponRepository couponRepository ;

    public Coupon createCoupon(Coupon coupon){
        if (couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("coupon code already exists") ;
        }
        return couponRepository.save(coupon) ;

    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll() ;
    }
}
