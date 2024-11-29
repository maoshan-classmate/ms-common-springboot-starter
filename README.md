#  ms-common-springboot-starter【自定义通用组件】

使用Java开发的自定义通用组件，开箱即用！让我们一起成长！

## 功能

1、可接收JSON格式的基本数据类型、包装类形、字符串类型。
2、支持常见数据类型的数据脱敏。
3、支持正则表达式进行数据脱敏。
4、支持自定义脱敏字符。
5、待开发......

## 注解

### @MsSingleRequestBody

注解使用方法同@RequestBody，但该注解只适用于JSON格式的基本数据类型、包装类形、字符串类型。除上述类型外，请使用@RequestBody注解。

### @MsSensitization

在对象属性上添加@MsSensitization，并选择脱敏数据的数据类型（目前只支持starter内提供的类型，后续会添加自定义脱敏规则）。

## @MsSingleRequestBody效果展示
### 请求参数
![07b178c67005b2224db563c8de879c28](https://github.com/user-attachments/assets/55a1312a-eafc-4e1f-9a66-4bdfe3e7921c)
### 后端接收
![1](https://github.com/user-attachments/assets/3637c69d-d4af-436d-961e-4024cc9824e5)

## @MsSensitization效果展示
![7c6b4b7965bc2d078b3cc04ea74f3eee](https://github.com/user-attachments/assets/44744387-3b63-4a7b-95bd-abd18d1b7467)
![4c7989e8e1b53457b50148f65d268066](https://github.com/user-attachments/assets/7012b4d3-258a-4523-953a-01d41e1c51ed)
