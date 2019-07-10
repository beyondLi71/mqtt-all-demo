### 使用说明
#### 一、项目说明
> 本项目基于mqtt通讯(需要自行安装mqtt服务端，推荐EMQ)，通过Java代码实现生产者以及消费者，达到生产者推送数据，消费者可以实时接收到生产者的数据。将生产者和消费者写到了同一个项目中是为了减少项目数量，两者并没有强关联关系。
具体代码解释在代码中有注释可以查看。
生产者:推送数据至EMQ。
消费者:实时监听主题，有消息被推送过来就进行获取并处理。
#### 二、环境说明
1.JDK1.8   
2.gradle 4.0 以上  
3.springboot2.x
#### 三、使用说明
1.com.beyondli.mqtt.config(生产者和消费者的配置文件)
2.com.beyondli.mqtt.customer(消费者代码)  
3.com.beyondli.mqtt.producer(生产者代码)
> 项目启动后,访问url http://localhost:7165/send?msg=测试数据，即可看到控制台分别输出推送成功和接收成功的内容，使用者可根据项目需要进行拆分使用。