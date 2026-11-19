package com.mallcloud.search.document;
import lombok.*; import org.springframework.data.annotation.Id; import org.springframework.data.elasticsearch.annotations.*; import java.math.BigDecimal;
@Data @NoArgsConstructor @AllArgsConstructor @Document(indexName="mall_products",createIndex=true)
public class ProductDocument {
 @Id private Long id; private Long categoryId;
 @Field(type=FieldType.Text,analyzer="standard") private String productName;
 @Field(type=FieldType.Text,analyzer="standard") private String subtitle;
 @Field(type=FieldType.Keyword,index=false) private String mainImage;
 @Field(type=FieldType.Double) private BigDecimal price;
 @Field(type=FieldType.Integer) private Integer status;
}
