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

import java.io.IOException;
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

    public int putProducts(List<ProductDto> productDtoList) {
        List<Product> productList =  DiscountMapper.INSTANCE.toEntities(productDtoList);
        for (Product product : productList) {
            productRepository.save(product);
        }
        return productList.size();
    }

    public ProductDto getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return DiscountMapper.INSTANCE.toDTO(product);

    }

    public List<ProductDto> searchProduct(SearchDto searchDto) {


        log.info("service : searchDto : " + searchDto);
        List<Product> productList = productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(), searchDto.getDiscount_type());
        log.info("service : ProductDtoList : " + productList);
        return DiscountMapper.INSTANCE.toDTOs(productList);
    }

    public List<ProductDto> GetProductGs25(Gs25SearchDto gs25SearchDto) {
        Object result = openFeign.feignGetGs25(gs25SearchDto.getPageNum(),gs25SearchDto.getPageSize(),gs25SearchDto.getSearchType());

        Gson gson = new Gson();
        Gs25PreDto gs25PreDto = gson.fromJson(result.toString(),Gs25PreDto.class);

        return gs25PreDtoToProductDtos(gs25PreDto);
    }

    public List<ProductDto> gs25PreDtoToProductDtos(Gs25PreDto gs25PreDto){

        List<ProductDto> list = new ArrayList<>();

        for(int i = 0 ; i < gs25PreDto.getResults().size() ; i ++){
            Gs25Product gs25Product = gs25PreDto.getResults().get(i);
            ProductDto productDto = ProductDto.builder()
                    .name(gs25Product.getGoodsNm())
                    .imageUrl(gs25Product.getAttFileNm())
                    .price((int)gs25Product.getPrice())
                    .discountType(convertDiscountType(gs25Product.getEventTypeNm()))
                    .convenienceStore(Constant.CONVENIENCE_GS25)
                    .build();

            list.add(productDto);
        }

        return list;
    }

    public List<ProductDto>  getProductCu(int pageIndex) {

        List<ProductDto> result = new ArrayList<>();
        try {
        for(int i = 0 ; i < pageIndex ; i++) {

            Thread.sleep(100);

            List<String> productCuNameList = new ArrayList<>();
            List<String> productCuImgList = new ArrayList<>();
            List<String> productCuPriceList = new ArrayList<>();
            List<String> productCuDiscountTypeList = new ArrayList<>();

            String url = "https://cu.bgfretail.com/event/plusAjax.do?pageIndex=" + i;

            // url 로 연결후 문서 정보 가져옴
            Document doc = Jsoup.connect(url).get();

            // 원하는 html 요소만 선택 후 필요한 값 만 List 에 담기
            Elements productName = doc.select("div.name");
            for (Element name : productName) {
                productCuNameList.add(name.text());
            }
            // img src 가져오기
            Elements productImg = doc.select("img.prod_img");
            for (Element img : productImg) {
                // 주소가 //로 시작할 때 제거 필터
                if (img.attr("src").startsWith("//")) {
                    productCuImgList.add("https://" + img.attr("src").substring(2));
                } else {
                    productCuImgList.add(img.attr("src"));
                }
            }
            // price 가져오기
            Elements productPrice = doc.select("div.price");
            for (Element price : productPrice) {
                // "원" 과 "," 제거
                productCuPriceList.add(price.text().replace("원", "").replace(",", ""));
            }

            // 할인 타입 (2+1, 1+1)
            Elements productDiscountType = doc.select("div.badge");
            for (Element discountType : productDiscountType) {

                productCuDiscountTypeList.add(discountType.text());
            }

            log.info(productCuNameList.toString());
            log.info(productCuPriceList.toString());
            log.info(productCuImgList.toString());
            log.info(productCuDiscountTypeList.toString());

            log.info(String.valueOf(productCuNameList.size()) + productCuPriceList.size() + productCuImgList.size() + productCuDiscountTypeList.size());


            if (productCuImgList.size() == productCuNameList.size() && productCuPriceList.size() == productCuNameList.size() && productCuDiscountTypeList.size() == productCuNameList.size()) {
                for (int j = 0; j < productCuImgList.size(); j++) {

                    result.add(ProductDto.builder()
                            .name(productCuNameList.get(j))
                            .price(Integer.parseInt(productCuPriceList.get(j)))
                            .imageUrl(productCuImgList.get(j))
                            .discountType(convertDiscountType(productCuDiscountTypeList.get(j)))
                            .convenienceStore(Constant.CONVENIENCE_CU)
                            .build());
                }
            }
        }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
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

    // intPageSize 입력은 intCurrPage 가 2이상일 때부터 제대로 작동 하므로
    // intCurrPage = 0 일때랑 1일때는 따로 처리를 해줘야 함
    public List<ProductDto> getProductSevenEleven(int pTab, int intPageSize, int intCurrPage) {
        List<ProductDto> result = new ArrayList<>();
        try {

            List<String> productSevenElevenNameList = new ArrayList<>();
            List<String> productSevenElevenPriceList = new ArrayList<>();
            List<String> productSevenElevenImgList = new ArrayList<>();
            List<String> productSevenElevenDiscountTypeList = new ArrayList<>();


            String url = "https://www.7-eleven.co.kr/product/listMoreAjax.asp?intPageSize="+intPageSize+"&pTab=" + pTab + "&intCurrPage=" + intCurrPage;
            Document doc = Jsoup.connect(url).get();

            Elements productName = doc.select("div.name");
            for (Element name : productName) {
                productSevenElevenNameList.add(name.text());
            }

            Elements productPrice = doc.select("div.price");
            for (Element price : productPrice) {
                productSevenElevenPriceList.add(price.text().replace(",",""));
            }

            Elements productImg = doc.getElementsByTag("img");
            for (Element img : productImg){
                productSevenElevenImgList.add("https://www.7-eleven.co.kr" + img.attr("src"));
            }

            Elements productDiscountType = doc.select("ul.tag_list_01");
            for (Element discountType : productDiscountType) {
                productSevenElevenDiscountTypeList.add(discountType.text());
            }


            log.info(productSevenElevenNameList.toString());
            log.info(productSevenElevenPriceList.toString());
            log.info(productSevenElevenImgList.toString());
            log.info(productSevenElevenDiscountTypeList.toString());

            if(productSevenElevenImgList.size() == productSevenElevenNameList.size() && productSevenElevenPriceList.size() == productSevenElevenNameList.size() && productSevenElevenDiscountTypeList.size() == productSevenElevenNameList.size()){
                for(int i = 0 ; i < productSevenElevenImgList.size() ; i++ ){

                    result.add(
                            ProductDto.builder()
                                    .name(productSevenElevenNameList.get(i))
                                    .price(Integer.parseInt(productSevenElevenPriceList.get(i)))
                                    .imageUrl(productSevenElevenImgList.get(i))
                                    .discountType(convertDiscountType(productSevenElevenDiscountTypeList.get(i)))
                                    .convenienceStore(Constant.CONVENIENCE_SEVEN_ELEVEN)
                                    .build());

                }
            }
            log.info(result.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;


    }
}
