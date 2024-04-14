package spharos.msg.domain.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spharos.msg.domain.brand.dto.BrandDetailDto;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.entity.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query(" SELECT new spharos.msg.domain.brand.dto.BrandResponseDto(b.brandName) FROM Brand b")
    List<BrandResponseDto> findAllBrandNames();

    @Query("SELECT  new spharos.msg.domain.brand.dto.BrandDetailDto(b.brandName,b.minDeliveryFee) from Brand b where b.id=:brandId")
    BrandDetailDto getBrandDetail(Long brandId);
}