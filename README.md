# file-service
文件上传下载服务
```java
// 设置响应头、以附件形式打开文件.  attachment 
response.setHeader("content-disposition", "attachment; fileName=" + new String(fileName.getBytes(),"utf-8"));

// 设置响应头、直接在浏览器中打开.  inline 
response.setHeader("content-disposition", "inline; fileName=" + new String(fileName.getBytes(),"utf-8"));
```
