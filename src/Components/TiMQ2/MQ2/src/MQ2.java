import tijos.framework.devicecenter.TiADC;
import tijos.framework.devicecenter.TiGPIO;
import tijos.framework.sensor.gas.TiMQ2;
import tijos.framework.sensor.gas.ITiMQ2EventListener;

/**
 * 1.此类实现了ITiMQ2EventListener可燃气体浓度报警事件监听接口<br>
 * 在<code>OnAlarm</code>与<code>OnRecovery</code>方法中不要阻塞处理事件太久，<br>
 * 本例程仅作为示例使用，实际使用时可根据采集到的电压值换算成当前可燃气体的浓度，根据不同环境和要求，该换算方式也不同，用户可根据实际情况自行处理。<br>
 * 可燃气体的浓度越大，则<code>getAnalogVoltage</code>方法获取到的电压值越高，最高为5V<br>
 * 因为TiJOS系统所有事件分发在同一个事件监听线程中，若阻塞<br>
 * 太久会影响其他事件的分发，导致事件响应不及时甚至丢失。<br>
 * <p>
 * 2.如果实际应用中，需要较长时间来处理某事件，建议在新的线程中<br>
 * 处理。<br>
 * <p>
 * @author Jason
 *
 */
class MQ2EventListener implements ITiMQ2EventListener {

	/*传感器报警事件处理*/
	public void OnAlarm(TiMQ2 mq2){
		System.out.println("Warning! Combustible gas concentration is high");
		System.out.println("MQ2 voltage = "+mq2.getAnalogVoltage() + " V, time(us):"+mq2.getEventTime());
	}
	
	/*传感器恢复事件处理*/
	public void OnRecovery(TiMQ2 mq2){
		System.out.println("The MQ2 has been Recovered, time(us):"+mq2.getEventTime());
	}
}

/**
 * 此类实现单个TiMQ2可燃气体报警功能演示<br>
 * TiMQ2事件的使用分为三步：<br>
 * 1.“资源分配”，使用tijos.runtime.deviceaccess包中TiGPIO类<code>open</code>方法分配GPIO对象（注：全局只能分配一次）。<br>
 * 使用tijos.runtime.deviceaccess包中TiADC类<code>open</code>方法分配ADC对象（注：全局只能分配一次）。<br>
 * 2.“资源绑定”，新创建TiMQ2对象，将其与1.中分配的GPIO对象以及ADC对象进行绑定。<br>
 * 3.“资源使用”，向TiMQ2对象中设置事件监听对象，事件监听类需要继承TiMQ2EventListener接口，根据发生的事件类型处理事件逻辑。<br>
 * <p>
 * @author Jason
 *
 */
public class MQ2 {
	/**
	 * 程序入口，由TiJOS调用
	 * @param args 入口参数， TiJOS中一直等于null
	 */
	public static void main(String[] args) {
		/*
		 * 定义使用的TiGPIO port
		 * 定义使用的TiADC port
		 */
		int adcPort0 = 0;
		int gpioPort0 = 0;
		/*
		 * 定义所使用的gpioPin
		 * */
		int gpioPin0 = 0;
		/*
		 * 资源分配，
		 * 将gpioPort与gpioPin0分配给TiGPIO对象gpio0
		 * 将adcPort0分配给TiADC对象adc0
		 */
		TiGPIO gpio0 = TiGPIO.open(gpioPort0, gpioPin0);
		TiADC adc0 = TiADC.open(adcPort0);
		/*
		 * 资源绑定，
		 * 创建TiMQ2对象mq2并将gpioPort、gpioPortPin和adcPort与其绑定
		 * Pin0<---->D0
		 * ADC <---->A0
		 */	
		TiMQ2 mq2 = new TiMQ2(gpio0, gpioPin0, adc0);
		/*
		 * 资源使用，
		 * 创建事件监听对象并设置事件监听
		 * 在事件监听中处理按键事件逻辑
		 * TiMQ2 使用的是硬件定点报警功能，即报警最低电压通过可变电阻设置，报警后可通过TiADC获取报警电压值。
		 */			
		MQ2EventListener lc = new MQ2EventListener();
		mq2.setEventListener(lc);
	
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}		
		}		
	}
}
