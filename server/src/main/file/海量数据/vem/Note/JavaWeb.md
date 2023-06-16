1.  统一返回结构

   - code
   - message
   - data

2. 统一包装处理

   Spring 中提供了一个类 `ResponseBodyAdvice` ，能帮助我们实现上述需求

   `ResponseBodyAdvice` 是对 Controller 返回的内容在 `HttpMessageConverter` 进行类型转换之前拦截，进行相应的处理操作后，再将结果返回给客户端。那这样就可以把统一包装的工作放到这个类里面。

   ```java
   // 如果引入了swagger或knife4j的文档生成组件，这里需要仅扫描自己项目的包，否则文档无法正常生成
   @RestControllerAdvice(basePackages = "com.example.demo")
   public class ResponseAdvice implements ResponseBodyAdvice<Object> {
       @Override
       public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
           // 如果不需要进行封装的，可以添加一些校验手段，比如添加标记排除的注解
           return true;
       }
     
   
       @Override
       public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
           // 提供一定的灵活度，如果body已经被包装了，就不进行包装
           if (body instanceof Result) {
               return body;
           }
           return Result.success(body);
       }
   }
   ```

   