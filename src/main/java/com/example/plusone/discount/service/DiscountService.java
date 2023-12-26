package com.example.plusone.discount.service;

import com.example.plusone.discount.utils.Constant;
import com.example.plusone.discount.dto.Gs25SearchDto;
import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.dto.SearchDto;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.gs25.dto.Gs25Product;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.openfeign.OpenFeign;
import com.example.plusone.discount.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final ProductRepository productRepository;
    private final OpenFeign openFeign;

    public List<ProductDto> productFilter(int discountType) {
        return DiscountMapper.INSTANCE.toDTOs(productRepository.findAllByDiscountType(discountType));
    }


    public void putProduct(ProductDto productDto){
        log.info(productDto.toString());
        productRepository.save(DiscountMapper.INSTANCE.toEntity(productDto));

    }

    public void putProducts(List<ProductDto> productDtoList) {
        List<Product> productList =  DiscountMapper.INSTANCE.toEntities(productDtoList);
        for (Product product : productList) {
            productRepository.save(product);
        }
    }

    public ProductDto getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return DiscountMapper.INSTANCE.toDTO(product);

    }

    public List<ProductDto> searchProduct(SearchDto searchDto) {

//        log.info(searchDto.toString());
//        log.info(String.valueOf(searchDto.getDiscount_type()));
//        System.out.println(searchDto.getDiscount_type());
//        log.info(searchDto.getQuery());


        List<Product> productList = productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(), searchDto.getDiscount_type());
//        log.info(productList.toString());
//        log.info(productRepository.findAllByNameContainsAndDiscountType("좋은",1).toString());

        return DiscountMapper.INSTANCE.toDTOs(productList);
    }

    public void insertGs25(Gs25SearchDto gs25SearchDto) {

//        log.info(gs25SearchDto.toString());
        Object result = openFeign.feignGetGs25(gs25SearchDto.getPageNum(),gs25SearchDto.getPageSize(),gs25SearchDto.getSearchType());
//        log.info(result.toString());
        Gson gson = new Gson();
        Gs25PreDto gs25PreDto = gson.fromJson(result.toString(),Gs25PreDto.class);
        List<ProductDto> list = gs25PreDtoToProductDtos(gs25PreDto);
        putProducts(list);

    }


    public List<ProductDto> gs25PreDtoToProductDtos(Gs25PreDto gs25PreDto){

        List<ProductDto> list = new ArrayList<>();

        for(int i = 0 ; i < gs25PreDto.getResults().size() ; i ++){
            Gs25Product gs25Product = gs25PreDto.getResults().get(i);
            ProductDto productDto = ProductDto.builder()
                    .name(gs25Product.getGoodsNm())
                    .image_url(gs25Product.getAttFileId())
                    .price((int)gs25Product.getPrice())
                    .discountType(convertDiscountType(gs25Product.getEventTypeNm()))
                    .convenienceStore(Constant.ConvenienceGs25)
                    .build();

            list.add(productDto);
        }

        return list;
    }

    public List<ProductDto>  getProductCu(int pageIndex) {


        try {

            List<String> ProductCuNameList = new ArrayList<>();
            List<String> ProductCuImgList = new ArrayList<>();
            List<String> ProductCuPriceList = new ArrayList<>();
            List<String> ProductCuDiscountTypeList = new ArrayList<>();

            String url = "https://cu.bgfretail.com/event/plusAjax.do?pageIndex="+pageIndex;

            // url 로 연결후 문서 정보 가져옴
            Document doc = Jsoup.connect(url).get();

            // 원하는 html 요소만 선택 후 필요한 값 만 List 에 담기
            Elements productName = doc.select("div.name");
            for (Element name : productName) {
                ProductCuNameList.add(name.text());
            }
            // img src 가져오기
            Elements productImg = doc.select("img.prod_img");
            for (Element img : productImg){
                // 주소가 //로 시작할 때 제거 필터
                if(img.attr("src").startsWith("//")){
                    ProductCuImgList.add("https://" + img.attr("src").substring(2));
                } else {
                    ProductCuImgList.add(img.attr("src"));
                }
            }
            // price 가져오기
            Elements productPrice = doc.select("div.price");
            for (Element price : productPrice){
                // "원" 과 "," 제거
                ProductCuPriceList.add(price.text().replace("원","").replace(",",""));
            }

            // 할인 타입 (2+1, 1+1)
            Elements productDiscountType = doc.select("div.badge");
            for (Element discountType : productDiscountType){

                ProductCuDiscountTypeList.add(discountType.text());
            }

            log.info(ProductCuNameList.toString());
            log.info(ProductCuPriceList.toString());
            log.info(ProductCuImgList.toString());
            log.info(ProductCuDiscountTypeList.toString());

            log.info(String.valueOf(ProductCuNameList.size()) + ProductCuPriceList.size() + ProductCuImgList.size() + ProductCuDiscountTypeList.size());

            List<ProductDto> result = new ArrayList<>();
            if(ProductCuImgList.size() == ProductCuNameList.size() && ProductCuPriceList.size() == ProductCuNameList.size()){
                for(int i = 0 ; i < ProductCuImgList.size() ; i++ ){

                    result.add(
                            ProductDto.builder()
                                    .name(ProductCuNameList.get(i))
                                    .price(Integer.parseInt(ProductCuPriceList.get(i)))
                                    .image_url(ProductCuImgList.get(i))
                                    .discountType(convertDiscountType(ProductCuDiscountTypeList.get(i)))
                                    .convenienceStore(Constant.ConvenienceCu)
                                    .build());

                }
            }

            log.info(result.toString());

            return result; // 결과 반환
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    int convertDiscountType(String type){
        if (type.equals("1+1")){
            return 1;
        } else if (type.equals("2+1")){
            return 2;
        } else {
            return 9;
        }
    }

    public void getProductSevenEleven() {
        try {

            List<String> ProductSevenElevenNameList = new ArrayList<>();
            List<String> ProductSevenElevenPriceList = new ArrayList<>();
            List<String> ProductSevenElevenImgList = new ArrayList<>();
            List<String> ProductSevenElevenDiscountTypeList = new ArrayList<>();


            String url = "https://www.7-eleven.co.kr/product/listMoreAjax.asp?intPageSize=10&pTab=1";
            Document doc = Jsoup.connect(url).get();

            Elements productName = doc.select("div.name");
            for (Element name : productName) {
                ProductSevenElevenNameList.add(name.text());
            }

            Elements productPrice = doc.select("div.price");
            for (Element price : productPrice) {
                ProductSevenElevenPriceList.add(price.text().replace(",",""));
            }

            Elements productImg = doc.getElementsByTag("img");
            for (Element img : productImg){
                ProductSevenElevenImgList.add("https://www.7-eleven.co.kr" + img.attr("src"));
            }

            Elements productDiscountType = doc.select("li.ico_tag_06");
            for (Element discountType : productDiscountType) {
                ProductSevenElevenDiscountTypeList.add(discountType.text());
            }


            log.info(ProductSevenElevenNameList.toString());
            log.info(ProductSevenElevenPriceList.toString());
            log.info(ProductSevenElevenImgList.toString());
            log.info(ProductSevenElevenDiscountTypeList.toString());

            List<ProductDto> result = new ArrayList<>();
            if(ProductSevenElevenImgList.size() == ProductSevenElevenNameList.size() && ProductSevenElevenPriceList.size() == ProductSevenElevenNameList.size()){
                for(int i = 0 ; i < ProductSevenElevenImgList.size() ; i++ ){

                    result.add(
                            ProductDto.builder()
                                    .name(ProductSevenElevenNameList.get(i))
                                    .price(Integer.parseInt(ProductSevenElevenPriceList.get(i)))
                                    .image_url(ProductSevenElevenImgList.get(i))
                                    .discountType(convertDiscountType(ProductSevenElevenDiscountTypeList.get(i)))
                                    .convenienceStore(Constant.ConvenienceSevenEleven)
                                    .build());

                }
            }
            log.info(result.toString());


        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
