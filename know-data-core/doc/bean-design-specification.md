# Bean Design Specification (Java实体设计规范)

# 0 基础 DTO
```
BaseObject implements Serializable { toString(){ReflectionToStringBuilder} }
BasePage extends BaseObject { currentPage, pageSize }

SortRequest extends BaseObject { fieldName , sortType[desc/asc] }

BaseRequest<T0> extends BaseObject { T0 params }
PageRequest<T1> extends BasePage { T1 params }

BaseResponse<T2> extends BaseObject { List<T2> records }	
PageResponse<T3> extends BasePage { totalCount, totalPages, List<T3> records, Map<String, Object> extData, Integer currentSize }

XResponse<T> { status, T data, errorCode, errorMessage }
```

# 1 前端/外部系统的入参请求

```shell
Header / Url / Body : 
    一般查询请求、数据传输场景 : BaseRequest|PageRequest<Tx = xxxxInput | Map>
        BaseRequest : 确定不分页时 | DTO
        PageRequest : 需要分页 或 可能分页时 | DTO
        =====
        泛型Tx=Map : 动态数据结构场景；图省事，开发高效，但不易维护 | DTO
        泛型Tx=xxxxInput : 开发效率偏低，但易于维护 [最佳实践/推荐] | DTO
            命名风格:
                [Batch]CreateXxxx[Records/Detail]Input | DTO
                [Batch]Delete/ClearXxxx[Records/Detail]Input | DTO
                [Batch]GetXxxx[Records/Detail]Input | DTO
                [Batch]UpdateXxxx[Records/Detail]Input | DTO

                [Batch]Import/UploadXxxx[Records/Detail]Input | DTO
                [Batch]ExportXxxx[Records/Detail]Input | DTO
                [Batch]DownloadXxxx[Records/Detail]Input | DTO

                [Batch]Enable/DisableXxxx[Records/Detail]Input | DTO

                [Batch]Apply/SubmitXxxxx[Records/Detail]Input | DTO
                [Batch]Register/UndoXxxx[Records/Detail]Input | DTO

                Xxxxx[Records/Detail]Vo : 被用于嵌套在 XxxxXxxxInput / XxxxXxxxOutput 中，作为一个输入/输出对象的属性 | VO
            形如: 
                GetAlarmDetailInput
                UpdateAlarmRecordsInput
                GetAlarmRecordVo
                AlarmMessageAccountVo
```


# 2 前端/外部系统的出参响应
```shell
Header / Url / Body :
    一般查询请求、数据传输场景 : XResponse<Tx=BaseResponse|PageResponse<Ty = xxxxOutput | Map>>
        BaseResponse : 确定不分页时 | DTO
        PageResponse : 需要分页 或 可能分页时 | DTO
        =====
        泛型Ty=Map : 图省事，开发高效，但不易维护 | DTO
        泛型Ty=xxxxOutput : 开发效率偏低，但易于维护 [最佳实践/推荐] | DTO
            命名风格:
                [Batch]GetXxxx[Records/Detail]Output | DTO
                [Batch]ExportXxxx[Records/Detail]Output | DTO
                [Batch]DownloadXxxx[Records/Detail]Output | DTO
                
                [Batch]UpdateXxxx[Records/Detail]Output | DTO
                [Batch]Enable/DisableXxxx[Records/Detail]Output | DTO
                [Batch]Register/UndoXxxx[Records/Detail]Output | DTO
                [Batch]Delete/ClearXxxx[Records/Detail]Output | DTO

                [Batch]ImportXxxx[Records/Detail]Output | DTO
                [Batch]UploadXxxx[Records/Detail]Output | DTO
                
                Xxxxx[Records/Detail]Vo : 被用于嵌套在 XxxxXxxxInput / XxxxXxxxOutput 中，作为一个输入/输出对象的属性 | VO
            形如:
                GetAlarmOutput
                ExportAlarmRecordVo
                GetAlarmMessageVo
                com.baidu.bms.services.domain.model.v0.alarmmessage.AlarmMessageAccountVo.java
```

# 3 数据库实体 | PO
```
通过包路径区分： *.po.Xxxx.java
    形如: 
        com.baidu.bms.services.domain.model.v0.usermanage.po.UserAccount.java
        com.baidu.bms.services.domain.model.v0.faultcode.po.FaultCodeRecord.java
```

# 4 当前阶段，我理想的应用工程的工程结构
```
domain
	model
	event
	exception
	constant

common
	util
	...

sdk : 对外开放的web接口、SDK

app : 即 biz 工程
    若为贫血模型：controller / service / dao(repository)

test
```


# 5 ddd 指导下的 VHR 项目
```
pom
	self-dependencies
	thrid-dependencies

common
	annotation
	aspect
	bean
	config
	constant
	interceptor
	util
sdk
services : domain / infrastructure / application / client / facade / starter
	application 工程
	
		@Service xxxxApplication 
	
	client 工程 : 一般为空，需具体情况讨论
	
	domain 工程 
		integration : 一般为空
		model : 重点 | 数据结构定义，统一存放于 domain 工程 的 *.domain.model.v0/v1/v2/... 的目录下 
			config
			common
			exception
			constant
			bean : PO(数据库实体) | DTO(数据传输对象) | VO(前端交互视图对象) | Constants(全局/公共常量) | enums | 
				v0|v1|v2|...
					{子模块名称}
		service : 重点
			event
				XxxxEvent extends ApplicationEvent : 定义事件
					spring 关键 API : ApplicationEvent (定义事件) / ApplicationContext.publishEvent(new XxxxEvent()) (发送事件) / @EventListener (监听，并处理事件)
			mapper
			reciver : kafka 消费者
				XxxxReciver
					核心方法 : doServe(...)
					一般会内置的属性: topic / xxxxRepository / xxxxService
			service :
				@Service XxxxService extends AbstractBaseService<XxxxInput/Void, XxxxOutput/Void>
					形如: 
						com.baidu.bms.services.domain.service.common.AlarmService/CanConfigService/RemoteConfigService/...
						com.baidu.vhrbackend.services.domain.service.v0/v1/v2.alarm.AddAlarmService/ExportAdvancedAlarmService/...
			statemachine : 状态机
				核心基础类: com.alibaba.cola.statemachine.Action/Condition/ 
				形如: 
					com.baidu.bms.services.domain.statemachine.remotecall.
						UpateRemoteCallTaskStatusAction implements Action<RemoteCallTaskStatusEnum, RemoteCallTaskEvent, VhrRemotecall>
						AddRemoteCallTaskAction implements Action<RemoteCallTaskStatusEnum, RemoteCallTaskEvent, VhrRemotecall>
			thread
			taskdispatch|xxljob : 任务调度
				核心基础类: AbstractJobHandler extends com.xxl.job.core.handler.IJobHandler
				命名风格 : XxxxxJobHandler 
				形如:
					@Component & @JobHandler("com.baidu.bms.services.domain.xxljob.AlarmMessageHandler") AlarmMessageHandler extends AbstractJobHandler
	facade 工程
		api
			controller
				v0/v1/v2/...
					命名风格: 
					形如: 
						@RestController @RequestMapping("v0") @Api("tags = "告警监控服务", description = "告警查询、核验、清除、导出、上传") AlarmController {
							@Resource
							private HttpServletRequest servletRequest;
							
							@Resource(name = "com.baidu.bms.services.application.v0.alarm.GetAlarmDetailApplication")
							private GetAlarmDetailApplication getAlarmDetailApplication;
							
							...
							
							@ApiOperation(value = "查询告警详情", notes = "client:operate_portal<br/>")
							@ApiResponses({ @ApiResponse( code = 200, message = ExceptionConstants.SYSTEM_INTERNAL_EXCEPTION_CODE + ":" + ExceptionConstants.SYSTEM_INTERNAL_EXCEPTION_MSG ) })
							@RequestMapping(value = "/admin/alarm/{uuid}", method = RequestMethod.GET)
							@OperationLog(resType = "故障监控", resName = "#resName", resUuid = "#uuid")
							public XResponse<GetAlarmDetailResponse> getAlarmDetail(@PathVariable("uuid") String uuid) throws Exception {
								...
								return XResponse.success(applicationDelegator.apply(getAlarmDetailApplication, request));
							}
							
							...
						}
			filter
				v0/v1/v2/...
			interceptor
				形如: @ControllerAdvice({"com.baidu.bms.services.facade.api.controller"}) BizExceptionAdvice
		model : TODO
			形如: 
				AddAlarmLevelChangeRequest extends BaseRequest
				DeleteAlarmLevelChangeResponse extends BaseResponse
	infrastructure 工程
		dao
			bigdataplatform
				形如 
					@FeignClient(value = "bdp-gateway-service", url = "${bigData.domainName}")
					public interface BigDataApi {
					    @PostMapping("${bigData.batchSignalValue}")
						XResponse<JSONObject> getSignalValueFromBigData(
							@RequestBody Map<String, Object> map,@RequestParam("nonce") String nonce
							, @RequestParam("timestamp") Long timestamp,@RequestParam("appKey") String appKey,@RequestParam("sign") String sign
						) throws Exception;
						
						...
					}
					
					@EnableRetry
					@Repository
					public class BigDataRepository {
					    @Resource
						private RestTemplate restTemplate;

						@Resource
						private BigDataApi bigDataApi;
						
						@Resource
						private VehicleRepository vehicleRepository;
						
						//...
						
						@Retryable(value = Exception.class, maxAttempts = 3)
						public CanSignalInfoListDto getExportCanSignalList( List<String> vinList, Long startTime, Long endTime , List<String> enNameList, String ecu ) {
							...
						}
					}
			filecenter
				核心工具类 : MiddleGroundUtil
				形如:
					@Component
					public class FileRepository {
						// ...
						
						public String uploadFile(UploadRequest request) {
							...
						}
					} 
			jpa.impl : JPA 即 Java Persistence API
				核心工具类 : JpaBaseRepository<T, ID> extends org.springframework.data.jpa.repository.support.SimpleJpaRepository<T, ID>
				形如 : 
					@Repository
					public class VhrAlarmLevelRuleRepository extends JpaBaseRepository<VhrAlarmLevelRule, Long> {
						...
					}
			logcenter
				LogRepository
			message
				PushMessageRepository
			organizationcenter
				OperatorRepository
			terminalcenter
				RemoteInstructionSendRepository
				VehicleRepository
		entity : TODO
			common
			dto
			jpa
			request
	starter
		形如:
			ApplicationExceptionHandler
			ApplicationRequestListener
			ApplicationShutdownHook
			ApplicationStartupHook
			
			I18nAddResource
			
			@Component
			ServerBootstrapLoader {
				@Resource
				private Environment env;
				
				@Resource
				private XxlJobScheduler xxlJobScheduler;
	
				@Resource
				private KafkaConsumerBootstrap kafkaConsumerBootstrap;
	
				@Resource
				private EventRecordsFileTopicReceiver eventRecordsFileTopicReceiver;
	
				@Resource
				private RemoteCallFileReceiver remoteCallFileReceiver;
				
				...
				@PostConstruct
				public void init() throws Exception {
					this.initKafka();
					logger.info("Kafka init success...");
					this.initXxlJob();
					logger.info("xxljob init success...");
				}
				
				/**
				 * 初始化xxljob
				 */
				private void initXxlJob() {
					xxlJobScheduler
							.registerXXLJob(ExportFileJobHandler.class)
							.registerXXLJob(RemoteCallExpireJobHandler.class)
							.registerXXLJob(AlarmMessageHandler.class)
							.registerXXLJob(DiagnosisTaskOverTimeHandler.class)
							.registerXXLJob(RemoteCallJobHandler.class)
							.registerXXLJob(DataExpireJobHandler.class)
							.registerXXLJob(CollectConfigStateHandler.class)
							.registerXXLJob(VehicleDiagnosisTaskOverTimeHandler.class)
							.registerXXLJob(VehicleAuthExpireTimeHandler.class)
							.registerXXLJob(DiagnosisUdsInstructionOverTimeHandler.class)
							.registerXXLJob(DiagnosisDisableHandler.class)
							.run();
				}

				/**
				 * 初始化kafka
				 */
				private void initKafka() {
					KafkaProducerBootstrap.initProducer(env.getProperty("bootstrap.servers"));
					kafkaConsumerBootstrap
							.setBootstrapServers(env.getProperty("bootstrap.servers"))
							.registerReceiver(
									eventRecordsFileTopicReceiver,
									remoteCallFileReceiver,
									alarmMessageReceiver,
									predictAlarmReceiver,
									diagnosisCloseLinkReceiver,
									remoteCallTriggerReceiver,
									exportHistoryReceiver,
									vehicleAuthReceiver,
									dataDeleteReceiver)
							.initConsumer();
				}
			}
			
			@EnableSwagger2
			@SpringCloudApplication
			@ComponentScan({"com.platform", "com.baidu","com.midplatform","doip.uds"})
			@EnableFeignClients({"com.platform", "com.baidu"})
			@EnableAsync
			VhrbackendStarter {
				private static final Logger logger = LoggerFactory.getLogger(VhrbackendStarter.class);
					
				public static void main(String[] args) {
				        String validAnnotationPath = "classpath*: com/baidu/bms/services/facade/api/controller/v0/*.class";
						I18nPropertiesUtil.addRuntimeErrorCode(validAnnotationPath);
						logger.info("Checking ErrorInfoConstants...{}", new ErrorInfoConstants());
						System.setProperty("es.set.netty.runtime.available.processors", "false");
						SpringApplication.run(VhrbackendStarter.class);
				}
			}


assmbly
	src\main\docker
		Dockerfile
		filebeat.yml.template
		start.sh
helm_vars

doc
	形如:
		alarm-desc.yml
			...
			
			alarm:
			  GetAlarmDetailService:
				version: v0
				client: operate_portal
				url: /admin/alarm/{uuid}
				desc: 查询告警详情
				input: GetAlarmDetailRequest
				output: GetAlarmDetailResponse
			
			...
		alarmshield-desc.yml			
```