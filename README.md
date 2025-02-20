# qywx-spring-boot-starter
企业微信API封装
``` 
<dependency>
  <groupId>com.github.shuaidd</groupId>
  <artifactId>qywx-spring-boot-starter</artifactId>
  <version>3.1.1</version>
</dependency>

```

使用示例
``` 
配置application.yml

qywx:
  corp-id: xxxx（企业号）
  application-list:
  - secret: Kx4sovYN5C0_MEzPY0cOymwbMhGmqdA9VjMFHrAKjdE
    agentId: 1000003
    application-name: little-helper
  - secret: DXB-FXVZNkLGUlLaIJy6CK67WD-dpN1HnPLIzNPo0N4
    agentId: 1000004
    application-name: reporter
  - secret: AfjvAed_ulqhK0OqTprDQ6xOSnqaT34ll2LsRe0D2NA
    application-name: address-book
  url: https://qyapi.weixin.qq.com
  public-path: cgi-bin

```
``` 
public class WeChatTest extends BaseTest {

    /**
    * 查询用户信息
    */
    @Test
    public void getUser(){
        weChatManager.addressBookService().getUser("13259220281", "address-book");
    }
}

实例 可以查看 qywx-spring-boot-example 模块
```

### 回调配置
![回调配置](https://upload-images.jianshu.io/upload_images/26817983-13eab16b4f158217.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 更新记录
``` 
2022-01-12  优化细节，支持spring boot 高版本
2021-11-25  新增微信客服模块接口[客服账号管理，接待人员管理 会话分配 消息收发 客户信息获取等]
2021-11-24  新增通讯录异步导出接口-[导出成员，导出成员详情，导出部门，导出标签成员，获取导出结果，导出任务完成通知]，使用方式详见测试用例 AddressBookTest
2021-08-05  修复应用消息回调空指针问题 issue#9 
2021-07-20  统一回调处理 例子见example模块
2021-07-20  已实现模块 通讯录管理，客户联系，身份认证，应用管理，消息推送，素材管理，OA【除自建审批流外】，效率工具
2021-07-15  补充OA模块新增的接口
2021-07-14  处理企业微信回调数据统一解析处理
```
