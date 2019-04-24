# spring-learn

#### 介绍
spring底层学习demo

## 1.Spring IOC容器

###  1. Spring注入方式
1. set方法注入
2. 构造方法注入

## 2.Bean的装配

###  1. Bean的作用域

1. singleton 单例获取
2. prototype 每次请求都创建实例

###  2. Bean的生命周期

1. 定义、初始化、使用、销毁
2. 待补充...

###  3. Spring aware - 让bean获取spring容器的服务

1. BeanNameAware  可以获取容器中bean的名称
2. ApplicationContextAware  当前的applicationContext， 这也可以调用容器的服务

###  4. Bean的自动装配

1. byName  根据属性名称自动装配-set注入
2. byType 在容器中查找指定属性类型进行装配-set注入
3. constructor 与byType类似-构造方法注入

