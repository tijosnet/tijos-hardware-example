# TiRelay1CH继电器控制例程

## 简介

本例程为TiRelay1CH的控制例程，对应目录如下：

- Relay1CH

## 适用TiKit开发板 

1.TiKit-T600-ESP8266A

## TiKit-T600-ESP8266A与TiRelay1CH连接说明 

### 电气连接

- DC5V<------>（+）电源
- GND<-------->（-）地
- PIN0<------>（S）信号线

### 示意图

![TiRelay1CH继电器控制例程](./Picture/TiRelay1CH继电器控制例程.jpg)

### 注意事项

1. 本例程为通过PIN引脚控制继电器的通断，从而达到小电压（控制端）控制大电压（输出端）的目的，输出端可接入功率以继电器所标的参数为准。
2. 当PIN引脚输出高电平时，继电器的COM引脚和NO引脚导通，当PIN0输出低电平时，继电器该两个引脚断开。
3. 用户可根据实际情况选择不同的PIN脚作为控制线