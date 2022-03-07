# 배달대행 API Document ( API Gateway )
### API document infomation
본 문서는 배달대행 서비스 관련한 rest-Ful API 정의 및 상세 내용을 설명한다.

```
- Project name : delivery agent
- Author : 윤순무
- Version : 0.1
- Date : 2019/10/17
- Update : 2019/10/21
```
_ _ _
### 수정이력
| version | version date | author | review |description |
|:-----:|:--------:|:--------:|:--------------------:|:----------|
| 0.1   | 2019/10/17  | 윤순무 | 윤순무 |연동규격의 최초 작성|
| 0.2   | 2019/10/17  | 윤순무 | 윤순무 |연동규격서의 포멧 설정|
| 0.3   | 2019/12/10  | 백상현 | 백상현 |주문API 정의|
| 0.4   | 2019/12/24  | 한상욱 | 한상욱 |API 추가|
| 0.5   | 2020/01/06  | 한상욱 | 한상욱 |LOGIN, 라이더 API 수정|
| 0.6   | 2021/06/10  | 박재성 | 박재성 |API 수정(REQ, RES 싱크)|
| 0.7   | 2021/07/01  | 박정훈 | 박정훈 |API 수정(연동주문접수)|

_ _ _
### 목차
[- 결과코드 리스트](#-result_cd--결과코드-리스트)  
[- 유저타입 리스트](#-user_type--유저타입-리스트)  
[- RESTful Header](#RESTful-Header)  
[- ORDER API](#ORDER-API)  
[- ADDRESS API](#ADDRESS-API)  
[- 주문 테이블](#-order--order-table--주문-테이블)   
[- 주문 접수](#-order--order-accept--주문-접수)  
[- 주문 요금산정](#-order--order-amount--주문-요금산정)  
[- 주문 알림](#-order--order-notify-주문-알림)  
[- 주문 정보 변경](#-order--order-update-주문-정보-변경)  
[- 주문 변경 알림](#-order--order-notify-update-주문-변경-알림)  
[- 대기 콜 삭제 알림](#-order--order-notify-catch-cancel--기사-배정에-따른-타-기사-대기-콜-삭제-알림)  
[- 기사 배정 요청](#-order--order-confirm--기사-배정요청)  
[- 기사 픽업 완료](#-order--order-pickup--기사-픽업-완료)  
[- 기사 배송 완료](#-order--order-complete--기사-배송완료)  
[- 주문 현황 조회](#-order--order-status--주문-현황-조회)  
[- 주문 현황 상세 조회](#-order--order-status-detail--주문-현황-상세-조회)  

_ _ _
### REFERENCE

---
##### [ result_cd ] 결과코드 리스트
> 결과코드 리스트를 정의한다.
>

| 결과코드| 결과 | 설명 |
|:------:|:-------:|:--------------------|
| 2001  | 성공 | 성공 시 |
| 5001  | 실패 | 아이디 미 등록 |
| 5002  | 실패 | 비밀번호 오류 |
| 5003  | 실패 | 최초 로그인(보안 인증 필요) |
| 5004  | 실패 | 미인증 PC 로그인 오류 |

---
##### [ user_type ] 유저타입 리스트
> 유저타입 리스트를 정의한다.
>

| 유저타입코드 | 설명 |
|:------:|:--------------------:|
| 1  | 본사 직원 |
| 2  | 개발사 |
| 3  | 전국총판 |
| 4  | 지역총판 |
| 5  | 지사 |
| 6  | 지점 |
| 7  | 직원(매니저) |
| 8  | 상점 |
| 9  | 기사 |

---
##### [ store_order_type ] 상점 오더 타입 리스트
> 상점 오더 타입 리스트를 정의한다.
>

| 유저타입코드 | 설명 |
|:------:|:--------------------:|
| 0  | 일반주문 |
| 1  | 정식연동(배달의민족) |
| 2  | 정식연동(배달천재) |
| 3  | 정식연동(매장천사) |
| 4  | 정신연동(포스피드) |
| 5  | 정식연동(푸드테크) |
| 7  | 정식연동(신세계아이앤씨) |
| 11  | 파싱연동(요기요) |
| 12  | 파싱연동(OKPOS) |
| 13  | 파싱연동(버거킹) |
| 14  | 파싱연동(롯데리아) |
| 15  | 파싱연동(바나나포스) |
| 17  | 파싱연동(이디야) |

---
##### [ command_type ] 커맨드타입 리스트
> 커맨드타입 리스트를 정의한다.
>

| 커맨드타입코드 | 설명 |
|:------:|:--------------------:|
| 0  | 주문 |
| 1  | 메시지 |
| 2  | 예약 주문 |
| 3  | 주문 변경 (값만 변경 되는 경우, 별도의 action 은 없음) |
| 4  | 지정 배차 |
| 5  | 공지 사항 |
| 6  | 로그 아웃 |
| 7  | 주문 조회(reload) |

_ _ _
### RESTful Header
> RESTful API 헤더에 포함되는 항목 정의
> Protocol: HTTPS

| 구분| 내용| 필수 | value | 설명 |
|:----:|:--------|:-------:|:--------------------|:--------------------|
|URL | Method:https://domain/api/v1/{API-URI} | ㅇ | API 별 URI |  |
|I | User-Token  | ㅇ | AES-256(CBC) + BASE64 | 인증 토큰 |
|I | ExipreAt  | ㅇ | seconds | 인증 토큰 lifetime (초) |
|I | Content-type  | ㅇ | application/json | 인증 토큰 lifetime (초) |
|I | Retry-After  | ㅇ | 3600 | 지연 시 에러처리(초) |
|I | API-SOURCE  | ㅇ | store, branch, storeapp, branchapp, riderapp, batch | API 호출 source |
|I | API-TYPE  | ㅇ | poll, event, mgmt | API 유형: 조회, 이벤트, 설정 |
|O | response  | ㅇ | 2XX, 4XX, 5XX | Response Status code  |
|O | Body | ㅇ | JSON format | API Body 정의 |

_ _ _

### 공통부
> 로그인을 제외한 모든 Request에 포함되는 항목
>

| 구분| 파라메타| 필수 | 설명 |value |
|:----:|:--------|:-------:|:--------------------|:------:|
|I | tr_id | ㅇ | API transaction ID  | 메시지를 최초 생성하는 곳에서 생성<br>예) account_id+YYYYMMDDHHmmss+Seq(2) |
|I | user_id  | ㅇ | 유저 로그인 ID  | 로그인 ID|
|I | account_id | ㅇ | 유저 계정  | 지사,상점, 기사 Account ID<br>로그인 ID와 별도 관리<br>예) 기사ID: BPXXXX-RRXXXX<br>**상점ID: BPXXXX-SSXXXX**<br>**지사ID: BBXXXX** |
|I | cast_type  | ㅇ | broadcasting type | 0: 전체 1: 개인 (default '0') |
|I | command_type  | ㅇ | stomp command type | 커맨드타입 리스트 참조 |
|I | user_type | ㅇ | 유저의 타입 | 유저타입 리스트 참조 |


_ _ _

### ORDER API

##### [ ORDER : ORDER ACCEPT ] 주문 접수
> 실시간 주문을 접수한다 (From 상점/지사).
>  
>>> 1. pickup_req_time 삭제 -> assign_delay_time 추가
>>> 2. address -> addr 원복
>>> 3. cook_time_mm -> cook_time 원복
>>> 4. store_type, store_order_type 추가

POST : https://domain/api/v1/order/accept

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | link_order_no | x | 외부 연동 주문 번호 | (스토리보드)주문 번호로 표시 |
|I | link_order_ch | x | 외부 연동 주문 채널 | (스토리보드)주문 채널로 표시 |
|I | order_memo | x | (오더접수) 주문관리 메모 |  |
|I | order_time | ㅇ | 배달 요청 시간(접수시간) | yyyymmddhh24miss |
|I | order_type | ㅇ | 배달 요청 구분 | 0: 배달요청<br>1: 자체배달 |
|I | branch_name | ㅇ | 지점 이름 (주문한 상점의 지점 이름) | |
|I | store_name | ㅇ | 상점 이름 | |
|I | store_type | ㅇ | 상점 유형 | 0:일반상점, 21: B2B프랜차이즈, 22: B2B영업대행 |
|I | store_order_type | ㅇ | 상점 주문 유형 | 상점타입 리스트 참조 |
|I | store_tel1 | ㅇ | 상점 연락처1 | |
|I | store_new_addr | x | 상점 신 주소 | |
|I | store_old_addr | x | 상점 구 주소 | |
|I | store_input_addr | x | 상점 입력주소 | |
|I | store_dong_info | ㅇ | 상점 동 정보 | |
|I | store_location_x | ㅇ | 상점 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | store_location_y | ㅇ | 상점 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | cook_time | x | 조리시간(오더 접수 시 픽업요청 시각) | 분단위 입력 |
|I | assign_delay_time | x | (오더접수) 지점설정배차지연시간 (분단위) |  |
|I | change_pickup_flag | x | (오더접수) 픽업 요청시간 변경 여부 | 0: 15분(기본), 1: 변경 |
|I | reservation_time | x | (오더접수) 배달 예약 시간 : CS나 APP에서 계산 해줘야함 | yyyymmddhh24miss |
|I | orderer_name | x | 주문고객 명 | |
|I | orderer_tel | ㅇ | 주문고객 연락처 | |
|I | order_note | x | 배달 시 필요한 정보 또는 유의사항 기재 | |
|I | delivery_new_addr | ㅇ | 배달지 신주소 | |
|I | delivery_old_addr | x | 배달지 구주소 | |
|I | delivery_input_addr | x | 배달지 입력주소 | |
|I | delivery_city_code | x | 배달지 시 정보 | |
|I | delivery_gu_code | x | 배달지 군,구 정보 | |
|I | delivery_dong_info | x | 배달지 동 정보 | |
|I | delivery_town_code | x | 배달지 읍,면 정보 | |
|I | delivery_location_x | ㅇ | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | ㅇ | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | ㅇ | 배달지까지의 거리(단위 : km) | |
|I | delivery_tip | x | (오더접수) 배달팁 : ex) 배달의 민족 처럼 현금으로 받는 배달료 : 따로 정산 필요 없음 | |
|I | rider_support_price | x | (관리자-배송수수료) 기사지원금(요금지원)  | |
|I | adjust_charge | x | (배달요금)요금조정 | |
|I | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|I | pay_flag | ㅇ | 결제 여부 | 0: 결제 전<br>1 :  결제 |
|I | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | order_action_type | ㅇ | 주문방식 | 0: 자동주문<br>1: 수동주문 |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|I | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"order_no" : "BP0001-SS0001022710000BD123",
	"link_order_no" : "",
	"link_order_ch" : "",
	"order_memo" : "",
	"order_time" : "20200101181022",
	"order_type" : "0",
	"branch_name" : "다우지점",
	"store_name" : "상점",
	"store_type" : "0",
	"store_order_type" : "0",
	"store_tel1" : "01012345678",
	"store_new_addr" : "경기도 용인시 수지구 죽전길 1",
	"store_old_addr" : "경기도 용인시 수지구 죽전동 123",
	"store_input_addr" : "",
	"store_dong_info" : "죽전동",
	"store_location_x" : "123.123456",
	"store_location_y" : "123.123457",
	"cook_time" : "10",
	"assign_delay_time" : "20",
	"reservation_time" : "",
	"orderer_name" : "홍길동",
	"orderer_tel" : "01022222222",
	"order_note" : "맛있게부탁합니다",
	"delivery_new_addr" : "경기도 용인시 수지구 죽전길 2",
	"delivery_old_addr" : "경기도 용인시 수지구 죽전동 456",
	"delivery_input_addr" : "",
	"delivery_city_code" : "용인시",
	"delivery_gu_code" : "수지구",
	"delivery_dong_info" : "죽전동",
	"delivery_town_code" : "",
	"delivery_location_x" : "123.123459",
	"delivery_location_y" : "123.123458",
	"distance" : "1.5",
	"delivery_tip" : "0",
	"rider_support_price" : "0",
	"adjust_charge" : "0",
	"food_price" : "20000",
	"cash_price" : "20000",
	"card_price" : "0",
	"coupon_price" : "0",
	"pay_flag" : "1",
	"order_pay_type" : "0",
	"order_action_type" : "1",
	"order_status" : "1",
	"assign_type" : "0"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---
##### [ ORDER : ORDER AMOUNT ] 주문 요금산정
> 도착지 주소 기반 주문 요금 산정(From 상점/지사).
>
>>> 1. system_order_time 추가  
>>> 1. rider_fee 삭제  

POST : https://domain/api/v1/order/amountbyaddr

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | store_location_x |ㅇ | 상점 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | store_location_y | ㅇ | 상점 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_city_code | ㅇ | 배달지 시 정보 | |
|I | delivery_gu_code | ㅇ | 배달지 군,구 정보 | |
|I | delivery_dong_code | ㅇ | 배달지 동 정보 | |
|I | delivery_town_code | ㅇ | 배달지 읍,면 정보 | |
|I | delivery_location_x | ㅇ | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | ㅇ | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | ㅇ | 배달지까지의 거리(단위 : km) | |
|I | rider_support_price | ㅇ | 기사 지원금 | |
|I | adjust_charge | ㅇ | (배달요금)요금조정 | |
|I | food_price | ㅇ | 물품 구매 금액 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | object|
|O | rider_support_price | ㅇ | 기사 지원금 | |
|O | adjust_charge | ㅇ | (배달요금)요금조정 | |
|O | weather_extra_charge | x | 기상 할증요즘 | 0: 기상할증OFF, 0>기상할증 금액 |
|O | holiday_extra_charge | ㅇ | 휴무일할증 | |
|O | rider_extra_charge_count  | ㅇ |  |  |
|O | rider_extra_charge_list | ㅇ | 지점 별 기사그룹 추가 할증(최대3개) | LIST |
|O-L | rider_extra_charge_name  | x | 추가 할증 이름  |  |
|O-L | rider_extra_charge_price  | x | 추가 할증 금액 |  |
|O | dong_extra_charge  | ㅇ | 지역 할증(동) 금액 |  |
|O | area_extra_charge  | ㅇ | 지역 할증(위치) 금액 |  |
|O | food_extra_charge  | ㅇ | 물품 할증 금액 |  |
|O | store_add_extra_charge  | ㅇ | (배송비) 추가할증금액 | 할증메뉴 -> 추가할증금액 |
|O | total_extra_charge | x | (배송비) 총할증금액 (기상할증금액+휴무할증금액+일반할증1~5+기타할증금액+추가거리할증) | |
|O | basic_charge | x | (배송비) 배송비(기본거리요금) | |
|O | add_distance_charge | x | (배송비) 추가거리요금 | |
|O | total_delivery_charge | x | (배송비) 총 배송비(배송비+총할증금액) | |
|O | system_order_time  | ㅇ | SYSTEM 주문시간(cs/app 시간이 다를 수 있으므로 system 시간으로 통일) | yyyymmddhh24missFFF |


>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"store_location_x" : "123.123456",
	"store_location_y" : "123.123457",
	"delivery_city_code" : "용인시",
	"delivery_gu_code" : "수지구",
	"delivery_dong_code" : "죽전동",
	"delivery_town_code" : "",
	"delivery_location_x" : "123.123459",
	"delivery_location_y" : "123.123458",
	"distance" : "1.5",
	"rider_support_price" : "0",
	"adjust_charge" : "0",
	"food_price" : "20000"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"rider_support_price" : "0",
		"adjust_charge" : "0",
		"weather_extra_charge" : "0",
		"holiday_extra_charge" : "0",
		"rider_extra_charge_count" : "2",
		"rider_extra_charge_list" : [
			{
				"rider_extra_charge_name" : "추가할증1",
				"rider_extra_charge_price" : "100"
			},
			{
				"rider_extra_charge_name" : "추가할증2",
				"rider_extra_charge_price" : "200"
			}
		],
		"dong_extra_charge" : "0",
		"area_extra_charge" : "0",
		"food_extra_charge" : "0",
		"store_add_extra_charge" : "0",
		"total_extra_charge" : "",
		"basic_charge" : "3000",
		"add_distance_charge" : "",
		"total_delivery_charge" : "3000",
		"system_order_time" : "20200226102100"
	}
}
```

---
##### [ ORDER : ORDER AMOUNT(DETAIL) ] 주문 요금산정 (상세보기)
> 도착지 주소 기반 주문 요금 산정. 상세보기(From 지사).
>

POST : https://domain/api/v1/order/amountbyaddr/detail

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | store_location_x |ㅇ | 상점 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | store_location_y | ㅇ | 상점 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_city_code | ㅇ | 배달지 시 정보 | |
|I | delivery_gu_code | ㅇ | 배달지 군,구 정보 | |
|I | delivery_dong_code | ㅇ | 배달지 동 정보 | |
|I | delivery_town_code | ㅇ | 배달지 읍,면 정보 | |
|I | delivery_location_x | ㅇ | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | ㅇ | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | ㅇ | 배달지까지의 거리(단위 : km) | |
|I | rider_support_price | ㅇ | 기사 지원금 | |
|I | adjust_charge | ㅇ | (배달요금)요금조정 | |
|I | food_price | ㅇ | 물품 구매 금액 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | object|
|O | rider_support_price | ㅇ | 기사 지원금 | |
|O | adjust_charge | ㅇ | (배달요금)요금조정 | |
|O | weather_extra_charge | x | 기상 할증요즘 | 0: 기상할증OFF, 0>기상할증 금액 |
|O | holiday_extra_charge | ㅇ | 휴무일할증 | |
|O | rider_extra_charge_count  | ㅇ |  |  |
|O | rider_extra_charge_list | ㅇ | 지점 별 기사그룹 추가 할증(최대3개) | LIST |
|O-L | rider_extra_charge_name  | x | 추가 할증 이름  |  |
|O-L | rider_extra_charge_price  | x | 추가 할증 금액 |  |
|O | total_extra_charge | x | (배송비) 총할증금액 (기상할증금액+휴무할증금액+일반할증1~5+기타할증금액+추가거리할증) | |
|O | dong_extra_charge  | ㅇ | 지역 할증(동) 금액 |  |
|O | area_extra_charge  | ㅇ | 지역 할증(위치) 금액 |  |
|O | food_extra_charge  | ㅇ | 물품 할증 금액 |  |
|O | store_add_extra_charge  | ㅇ | (배송비) 추가할증금액 | 할증메뉴 -> 추가할증금액 |
|O | basic_charge | x | (배송비) 배송비(기본거리요금) | |
|O | add_distance_charge | x | (배송비) 추가거리요금 | |
|O | total_delivery_charge | x | (배송비) 총 배송비(배송비+총할증금액) | |
|O | rider_fees | x | (관리자-배송요금) 기사수수료 | |
|O | manage_fees | x | (관리자-배송수수료) 관리 수수료 | |
|O | control_fees | x | (관리자-배송수수료) 관제 수수료(=공유지점 수수료) | |
|O | system_order_time  | ㅇ | SYSTEM 주문시간(cs/app 시간이 다를 수 있으므로 system 시간으로 통일) | yyyymmddhh24missFFF |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"order_no" : "NG0000-LG0000-BB0000-BP0001-SS000022710000BD123",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"store_location_x" : "123.123456",
	"store_location_y" : "123.123457",
	"delivery_city_code" : "용인시",
	"delivery_gu_code" : "수지구",
	"delivery_town_code" : "",
	"delivery_dong_code" : "죽전동",
	"delivery_location_x" : "123.123459",
	"delivery_location_y" : "123.123458",
	"Distance" : "1.5",
	"rider_support_price" : "0",
	"adjust_charge" : "0",
	"food_price" : "20000"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"rider_support_price" : "0",
		"adjust_charge" : "0",
		"weather_extra_charge" : "0",
		"holiday_extra_charge" : "0",
		"rider_extra_charge_count" : "2",
		"rider_extra_charge_list" : [
			{
				"rider_extra_charge_name" : "추가할증1",
				"rider_extra_charge_price" : "100"
			},
			{
				"rider_extra_charge_name" : "추가할증2",
				"rider_extra_charge_price" : "200"
			}
		],
		"dong_extra_charge" : "0",
		"area_extra_charge" : "0",
		"food_extra_charge" : "0",
		"store_add_extra_charge" : "0",
		"total_extra_charge" : "",
		"basic_charge" : "3000",
		"add_distance_charge" : "",
		"total_delivery_charge" : "3000",
		"rider_fees" : "2700",
		"manage_fees" : "150",
		"control_fees" : "150",
		"system_order_time" : "20200226102100"
	}
}
```

---

##### [ ORDER : ORDER NOTIFY] 주문 알림
> 실시간 주문정보를 통지/알림 (To 기사App/상점App/지사App).
> WebSocket API  

POST : https://domain/api/v1/order/notify

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | 접수 상점 관리 지점 ID |
|I | shared_branch_id | x | 공유 지사/지점 account_id | 공유 지사/지점 ID |
|I | store_id | ㅇ | 상점 account_id | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | link_order_no | x | 외부 연동 주문 번호 | (스토리보드)주문 번호로 표시 |
|I | link_order_ch | x | 외부 연동 주문 채널 | (스토리보드)주문 채널로 표시 |
|I | order_memo | ㅇ | (오더접수) 주문관리 메모 |  |
|I | order_time | ㅇ | 배달 요청 시간(접수시간) | yyyymmddhh24miss |
|I | branch_name | ㅇ | 지점 이름 | 접수 상점 관리 지점 명 |
|I | store_user_id | x | 상점 계정 LOGINID | |
|I | store_name | ㅇ | 상점 이름 | |
|I | store_tel1 | ㅇ | 상점 연락처1 | |
|I | store_new_addr | x | 상점 신 주소 | |
|I | store_old_addr | x | 상점 구 주소 | |
|I | store_input_addr | x | 상점 입력주소 | |
|I | store_dong_info | ㅇ | 상점 동 정보 | |
|I | store_location_x | ㅇ | 상점 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | store_location_y | ㅇ | 상점 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | store_order_type | ㅇ | 상점 주문 유형 | 상점타입 리스트 참조 |
|I | store_balance | ㅇ | 상점 잔여 포인트 |  |
|I | cook_time | x | 조리시간(오더 접수 시 픽업요청 시각) | 분단위 입력 |
|I | pickup_req_time | ㅇ | 픽업 요청한 시간 (order_time + cook_time  + rider_delay_duration) | yyyymmddhh24miss |
|I | accept_lap_time | x | 접수 경과 시간 | HH:MM |
|I | status_lap_time | x | 상태 경과 시간 | HH:MM |
|I | pickup_remain_min | x | 픽업 남은 시간 | 분 |
|I | store_company_crn | ㅇ | (상점) 사업자 번호 | |
|I | reservation_time | x | (오더접수) 배달 예약 시간 : CS나 APP에서 계산 해줘야함 | yyyymmddhh24miss |
|I | orderer_name | x | 주문고객 명 | |
|I | orderer_tel | ㅇ | 주문고객 연락처 | |
|I | order_note | x | 배달 시 필요한 정보 또는 유의사항 기재 | |
|I | delivery_new_addr | ㅇ | 배달지 신주소 | |
|I | delivery_old_addr | x | 배달지 구주소 | |
|I | delivery_input_addr | x | 배달지 입력주소 | |
|I | delivery_dong_info | x | 배달지 동 정보 | |
|I | delivery_location_x | ㅇ | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | ㅇ | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | ㅇ | 배달지까지의 거리(단위 : km) | |
|I | delivery_tip | x | (오더접수) 배달팁 : ex) 배달의 민족 처럼 현금으로 받는 배달료 : 따로 정산 필요 없음 | |
|I | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|I | basic_charge | x | (배송비) 배송비(기본거리요금+추가거리요금) | |
|I | total_delivery_charge | x | (배송비) 총 배송비(배송비+총할증금액) | |
|I | weather_extra_charge | x | 기상 할증요즘 | 0: 기상할증OFF, 0>기상할증 금액 |
|I | holiday_extra_charge | ㅇ | 휴무일할증 | |
|I | rider_extra_charge_count  | ㅇ |  |  |
|I | rider_extra_charge_list | ㅇ | 지점 별 기사그룹 추가 할증(최대3개) | LIST |
|I-L | rider_extra_charge_name  | x | 추가 할증 이름  |  |
|I-L | rider_extra_charge_price  | x | 추가 할증 금액 |  |
|I | dong_extra_charge  | ㅇ | 지역 할증(동) 금액 |  |
|I | area_extra_charge  | ㅇ | 지역 할증(위치) 금액 |  |
|I | food_extra_charge  | ㅇ | 물품 할증 금액 |  |
|I | store_add_extra_charge  | ㅇ | 추가 할증 |  |
|I | add_distance_charge | x | (배송비) 추가거리요금 | |
|I | total_extra_charge | x | (배송비) 총할증금액 (기상할증금액+휴무할증금액+일반할증1~5+기타할증금액+추가거리할증) | |
|I | pay_flag | x | 결제 여부 | 0: 결제 전<br>1 :  결제 |
|I | payed_price | x | 결제 완료 금액 | |
|I | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | real_pay_type | x | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|I | food_complete_flag | x | (상점-음식완료) 물품(음식) 완료 여부 | 0: 미완성 1: 완성 |
|I | food_complete_time | x | (상점-음식완료) 물품(음식) 완료 시간 | |
|I | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|I | assign_time | x | 배차성공시간(기사배정) - 표시(hh:mm) | yyyymmddhh24miss |
|I | pickup_time | x | 라이터가 물품 픽업한 시간(픽업시간) - 표시(hh:mm) | yyyymmddhh24miss |
|I | success_time | x | 배달완료시간 | yyyymmddhh24miss |
|I | cancel_time | x | 배달취소시간 | yyyymmddhh24miss |
|I | cancel_cause | x | 배달 취소 원인 | 0: 해당사항없음<br>1: 고객 거부 |
|I | cancel_text | x | 배달 취소 원인 (직접입력) |  |
|I | canceler | x | 배달 취소자 지점/ 상점/ 기사 + account_id + ACCOUNTNAME | |
|I | card_approval_no | x | 신용카드 결제 승인번호 | 필수항목 |
|I | rider_branch_id | ㅇ | 관제지점 account_id | 기사소속지점 account_id |
|I | rider_branch_name | ㅇ | 관제지점 명 | 기사소속지점 명 |
|I | rider_id | x | 기사 ID | |
|I | rider_name | x | 배차된 배달기사 이름 | |
|I | rider_user_id | x | 기사 계정 LOGIN ID | |
|I | rider_tel | x | 배차된 배달기사 무선 연락처 | |
|I | delivery_fees | x |(관리자-배송요금) 수수료 합계 (기사수수료+관리수수료+관제수수료+요금지원) |  |
|I | rider_fees | x | 배달기사 수수료 | 기본요금 + 추가거리 할증요금 |
|I | manage_fees | x | 관리 수수료 | |
|I | control_fees | x | 관제 수수료(=공유지점 수수료) | |
|I | rider_support_price | x | 기사 지원금 | |
|I | status_change_time | x | 주문 변경 시간 | |
|I | data_update_date | x | 데이터 수정 일자 | yyyymmddhh24miss |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"shared_branch_id" : "",
	"store_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"order_no" : "NG0000-LG0000-BB0000-BP0001-SS000022710000BD123",
	"link_order_no" : "",
	"link_order_ch" : "",
	"order_memo" : "",
	"order_time" : "20200101181022",
	"branch_name" : "다우지점",
	"store_user_id" : "storeHong",
	"store_name" : "상점",
	"store_tel1" : "01012345678",
	"store_new_addr" : "경기도 용인시 수지구 죽전길 1",
	"store_old_addr" : "경기도 용인시 수지구 죽전동 123",
	"store_input_addr" : "",
	"store_dong_info" : "죽전동",
	"store_location_x" : "123.123456",
	"store_location_y" : "123.123457",
	"store_order_type" : "0",
	"store_balance" : "100000",
	"cook_time" : "10",
	"pickup_req_time" : "20200101182022",
	"accept_lap_time" : "00:20",
	"status_lap_time" : "00:20",
	"pickup_remain_min" : "120",
	"store_company_crn" : "123-12-1234",
	"reservation_time" : "",
	"orderer_name" : "홍길동",
	"orderer_tel" : "01022222222",
	"order_note" : "맛있게부탁합니다",
	"delivery_new_addr" : "경기도 용인시 수지구 죽전길 2",
	"delivery_old_addr" : "경기도 용인시 수지구 죽전동 456",
	"delivery_input_addr" : "",
	"delivery_dong_info" : "죽전동",
	"delivery_location_x" : "123.123459",
	"delivery_location_y" : "123.123458",
	"distance" : "1.5",
	"delivery_tip" : "0",
	"food_price" : "20000",
	"cash_price" : "20000",
	"card_price" : "0",
	"coupon_price" : "0",
	"basic_charge" : "3000",
	"total_delivery_charge" : "3000",
	"weather_extra_charge" : "0",
	"holiday_extra_charge" : "0",
	"rider_extra_charge_count" : "2",
	"rider_extra_charge_list" : [
		{
			"rider_extra_charge_name" : "추가할증1",
			"rider_extra_charge_price" : "100"
		},
		{
			"rider_extra_charge_name" : "추가할증2",
			"rider_extra_charge_price" : "200"
		}
	],
	"dong_extra_charge" : "0",
	"area_extra_charge" : "0",
	"food_extra_charge" : "0",
	"store_add_extra_charge" : "0",
	"add_distance_charge" : "0",
	"total_extra_charge" : "0",
	"pay_flag" : "0",
	"payed_price" : "0",
	"order_pay_type" : "0",
	"real_pay_type" : "0",
	"order_status" : "1",
	"food_complete_flag" : "0",
	"food_complete_time" : "",
	"assign_type" : "0",
	"assign_time" : "20200101182032",
	"pickup_time" : "20200101182032",
	"success_time" : "",
	"cancel_time" : "",
	"cancel_cause" : "",
	"cancel_text" : "",
	"canceler" :  "",
	"card_approval_no" : "",
	"rider_branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"rider_branch_name" : "다우지점",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"rider_name" : "홍길동",
	"rider_user_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"rider_tel" : "01012341234",
	"delivery_fees" : "3500",
	"rider_fees" : "2700",
	"manage_fees" : "150",
	"control_fees" : "150",
	"rider_support_price" : "0",
	"status_change_time" : "20200101182032",
	"data_update_date" : "20200101182032"

}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER USERSYNC ] 유저 주문 정보 동기화
> 유저 주문 정보 동기화.
>

POST : https://domain/api/v1/order/usersync


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |
|O | assign_delay_time | ㅇ | 지점설정배차지연시간 (분단위) |  |
|O | pickup_delay_time | ㅇ | 지점설정픽업지연시간 (분단위) |  |
|O | balance | ㅇ | 지점 또는 상점 잔액 | |
|O | status | ㅇ | 상태 |0: 해지 1: 중지 2: 정상 |
|O | extra_flag | ㅇ | 기상/휴무일 할증 적용여부 | 0: 미변경, 1: 변경 |
|O | rider_sort_flag | ㅇ | 기사앱 오더리스트 정렬 기준값 | 0: 시간순(기본값), 1: 긴급순, 2: 거리순 |
|O | rider_sort_count | ㅇ | 기사앱 오더리스트 출력 카운트 제한 값 | 0: 제한 없음, 1 ~ : 해당 카운트 만큼 출력 |
|O | order_pause | ㅇ | 주문 일시정지 여부 | 0: 미적용, 1: 적용 |
|O | display_extra_charge | ㅇ | 상점 할증 보기 여부 | 0: 미적용, 1: 적용 |
|O | tmp_assign_flag | ㅇ | 가배차 설정 | 0: 미적용, 1: 적용 |
|O | day_standard_time | ㅇ | 하루 기준 시간 설정 | 08:00, 00:00 |
|O | rider_order_cancel_flag | ㅇ | 기사 오더취소 설정 | 0: 불가능, 1: 가능 |
|O | holiday_extra_flag | ㅇ | 휴무일 자동 할증 설정 | 0: 미사용, 1: 사용 |
|O | open_time | ㅇ | 배달대행 업무 운영 시간 (시작) | HH:mm(시:분) |
|O | close_time | ㅇ | 배달대행 업무 운영 시간 (종료) | HH:mm(시:분) |
|O | public_holiday_list | ㅇ | 지정 공휴일 날짜 설정 | [월일, MMdd, MMdd] |
|O | temporary_holiday_list | ㅇ | 임시 공휴일 날짜 설정 | [연월일, YYYYMMdd, YYYYMMdd] |

>  Request
```json
{
	"tr_id" : "BP00012020010214192201",
	"user_id" : "storeHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "8"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"assign_delay_time" : "20",
		"pickup_delay_time" : "20",
		"balance" : "10000",
		"status" : "1",
		"extra_flag" : "1",
		"rider_sort_flag" : "0",
		"rider_sort_count" : "0",
		"order_pause" : "0",
		"tmp_assign_flag" : "0",
		"day_standard_time" : "08:00",
		"rider_order_cancel_flag" : "0",
		"holiday_extra_flag" : "0",
		"open_time" : "09:00",
		"close_time" : "23:30",
		"public_holiday_list" : ["0101", "0301", "0901"],
		"temporary_holiday_list" : ["20210102", "20210302", "20210902"]
	}
}
```

---

##### [ USERSYNC NOTIFY] 유저 정보 동기화 (Notify)
> 실시간 유저 주문 정보 동기화.
> command_type = 9 (고정) <- command_type이 9인 Notify에 대해서 동기화 처리
> cast_type은 변동 가능
> assign_delay_time, pickup_delay_time, balance, status 필드에 대해서 null이 아닌 필드의 값을 반영처리
> WebSocket Noti API  

POST :


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|O | data | ㅇ | DATA | - |
|O | assign_delay_time | ㅇ | 지점설정배차지연시간 (분단위) |  |
|O | pickup_delay_time | ㅇ | 지점설정기본픽업지연시간 (분단위) |  |
|O | balance | ㅇ | 지점 또는 상점 잔액 | |
|O | status | ㅇ | 상태 |0: 해지 1: 중지 2: 정상 |

>  Request
```json
```

>  Response
```json
{
	"command_type" : "9",
	"cast_type" : "",
	"target_user_type" : "",
	"target_account_id" : "",
	"data": {
		"assign_delay_time":"30",
		"pickup_delay_time":null,
		"balance":"12300",
		"status":null
	}
}
```



---

##### [ ORDER : ORDER UPDATE ] 주문 정보 변경
> 주문 정보 변경 (From 지사 Client/App).
> 변경 된 항목만
>
> >> 1. food_complete_flag 추가

POST : https://domain/api/v1/order/update


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | order_pay_type | ㅇ | DATA | object |
|I-M-M | order_pay_type | x | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I-M-M | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I-M-M | price_info | ㅇ | DATA | object |
|I-M-M-M | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M-M-M | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M-M-M | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|I-M | rider | ㅇ | DATA | object |
|I-M-M | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|I-M-M | rider_id | x | 기사 ID | |
|I-M-M | rider_name | x | 배차된 배달기사 이름 | |
|I-M-M | rider_tel | x | 배차된 배달기사 무선 연락처 | |
|I-M | orderer_tel | ㅇ | DATA | object |
|I-M-M | orderer_tel | x | 주문고객 연락처 | |
|I-M | delivery_addr | ㅇ | DATA | object |
|I-M-M | delivery_new_addr | x | 배달지 신주소 | |
|I-M-M | delivery_old_addr | x | 배달지 구주소 | |
|I-M-M | delivery_input_addr | x | 배달지 입력주소 | |
|I-M-M | delivery_dong_info | x | 배달지 동 정보 | |
|I-M-M | delivery_city_code | x | 배달지 시 정보 | |
|I-M-M | delivery_gu_code | x | 배달지 군,구 정보 | |
|I-M-M | delivery_town_code | x | 배달지 읍,면 정보 | |
|I-M-M | delivery_location_x | x | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I-M-M | delivery_location_y | x | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I-M-M | distance | x | 배달지까지의 거리(단위 : km) | |
|I-M | pickup_req_time | ㅇ | DATA | object |
|I-M-M | pickup_req_time | x | 픽업 요청한 시간 (order_time + cook_time  + rider_delay_duration) | yyyymmddhh24miss |
|I-M | order_note | ㅇ | DATA | object |
|I-M-M | order_note | x | 배달 시 필요한 정보 또는 유의사항 기재 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
    	"order_pay_type" : {
    		"order_pay_type" : "1",
    		"price_info" : {
                  "food_price" : "20000",
                  "cash_price" : "0",
                  "card_price" : "0",
                  "coupon_price" : "0"
            }
    	},
    	"rider" : {
    		"assign_type" : "0",
    		"rider_id" : "BP0001-RR0001",
    		"rider_name" : "홍길동",
    		"rider_tel" : "01012341234"
    	},
    	"orderer_tel" : {
    		"orderer_tel" : "01033333333"
    	},
    	"delivery_addr" : {
    		"delivery_new_addr" : "경기도 용인시 수지구 죽전길 3",
    		"delivery_old_addr" : "경기도 용인시 수지구 죽전동 678",
    		"delivery_input_addr" : "",
    		"delivery_dong_info" : "죽전동",
    		"delivery_town_code" : "",
    		"delivery_city_code" : "경기도 용인시",
    		"delivery_gu_code" : "수지구",
    		"delivery_location_x" : "123.323459",
    		"delivery_location_y" : "123.323458",
    		"distance" : "1.5"
    	},
    	"pickup_req_time" : {
    		"pickup_req_time" : "20200114153020"
    	},
    	"order_note" : {
    		"order_note" : "맛있게부탁합니다"
    	}
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---
##### [ ORDER : ORDER NOTIFY UPDATE] 주문 변경 알림
> 주문 변경 정보를 통지/알림 (To 기사App/상점App/지사App).
> WebSocket API
>
> >> 1. food_complete_flag 추가

POST : https://domain/api/v1/order/notify/update

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | assign_time | x | 배차성공시간(기사배정) | yyyymmddhh24miss |
|I | pickup_req_time | x | 픽업 요청한 시간 (order_time + cook_time  + rider_delay_duration) | yyyymmddhh24miss |
|I | pickup_time | x | 라이터가 물품 픽업한 시간(픽업시간) | yyyymmddhh24miss |
|I | success_time | x | 배달완료시간 | yyyymmddhh24miss |
|I | cancel_time | x | 배달취소시간 | yyyymmddhh24miss |
|I | cancel_cause | x | 배달 취소 원인 | 0 : 해당사항없음<br> 1: 고객거부(물품 오류)<br> 2: 고객거부(단순변심) |
|I | cancel_text | x | 배달 취소 원인 (직접입력) |  |
|I | orderer_tel | x | 주문고객 연락처 | |
|I | order_note | x | 배달 시 필요한 정보 또는 유의사항 기재 | |
|I | delivery_new_addr | x | 배달지 신주소 | |
|I | delivery_old_addr | x | 배달지 구주소 | |
|I | delivery_input_addr | x | 배달지 입력주소 | |
|I | delivery_dong_info | x | 배달지 동 정보 | |
|I | delivery_location_x | x | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | x | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | x | 배달지까지의 거리(단위 : km) | |
|I | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I | payed_price | x | 결제 완료 금액 | |
|I | pay_flag | x | 결제 여부 | 0: 결제 전<br>1 :  결제 |
|I | delivery_fees | x | 배달기사 수수료 합계 | 상점별 기본 거리금액 + 상점별 날씨할증액 + 상점별 시간할증액 + 상점별 거리추가할증액  + 상점추가금액 |
|I | order_pay_type | x | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | real_pay_type | x | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | order_status | x | 현재 진행 상태 표시 | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|I | food_complete_flag | x | (상점-음식완료) 물품(음식) 완료 여부 | 0: 미완성 1: 완성 |
|I | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|I | location_x | ㅇ | 라이더 위치 X 좌표 | |
|I | location_y | ㅇ | 라이더 위치 Y 좌표 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"assign_time" : "20200114152020",
	"pickup_req_time" : "20200114153020",
	"pickup_time" : "20200114153020",
	"success_time" : "",
	"cancel_time" : "",
	"cancel_cause" : "0",
	"orderer_tel" : "01033333333",
	"order_note" : "맛있게부탁합니다",
	"delivery_new_addr" : "경기도 용인시 수지구 죽전길 3",
	"delivery_old_addr" : "경기도 용인시 수지구 죽전동 678",
	"delivery_input_addr" : "",
	"delivery_dong_info" : "죽전동",
	"delivery_location_x" : "123.323459",
	"delivery_location_y" : "123.323458",
	"distance" : "1.5",
	"food_price" : "20000",
	"payed_price" : "20000",
	"pay_flag" : "1",
	"delivery_fees" : "3300",
	"order_pay_type" : "2",
	"real_pay_type" : "2",
	"order_status" : "3",
	"food_complete_flag" : "1",
	"assign_type" : "0",
	"location_x" : "123.123456",
	"location_y" : "123.123457"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER TEL UPDATE ] 주문 정보 변경 (고객 전화 번호)
> 주문 고객 전화 번호 변경 (From 상점 Client/App).
>

POST : https://domain/api/v1/order/update/tel


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | orderer_tel | x | 주문고객 연락처 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"orderer_tel" : "01033333333"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER ADDRESS UPDATE ] 주문 정보 변경 (고객 주소)
> 주문 고객 주소 변경 (From 상점 Client/App).
>

POST : https://domain/api/v1/order/update/address


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I | delivery_new_addr | ㅇ | 배달지 신주소 | |
|I | delivery_old_addr | x | 배달지 구주소 | |
|I | delivery_input_addr | x | 배달지 입력주소 | |
|I | delivery_city_code | x | 배달지 시 정보 | |
|I | delivery_gu_code | x | 배달지 군,구 정보 | |
|I | delivery_dong_info | ㅇ | 배달지 동 정보 | |
|I | delivery_town_code | x | 배달지 읍,면 정보 | |
|I | delivery_location_x | ㅇ | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | delivery_location_y | ㅇ | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|I | distance | ㅇ | 배달지까지의 거리(단위 : km) | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"delivery_new_addr" : "경기도 용인시 수지구 죽전길 3",
		"delivery_old_addr" : "경기도 용인시 수지구 죽전동 678",
		"delivery_input_addr" : "",
		"delivery_city_code" : "경기도 용인시",
		"delivery_gu_code" : "수지구",
		"delivery_dong_info" : "죽전동",
		"delivery_town_code" : "",
		"delivery_location_x" : "123.323459",
		"delivery_location_y" : "123.323458",
		"distance" : "1.5"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (결제 수단)
> 주문 결제 수단 변경 (From 상점 Client/App).
> story board : 관리자 pc - 29page
>

POST : https://domain/api/v1/order/update/paytype


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I-M | price_info | ㅇ | 주문금액 | object |
|I-M-M | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I-M-M | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M-M | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M-M | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |
|O | org_pay_type | x | 주문시 이전 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|O | order_pay_type | x | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"order_pay_type" : "3",
		"price_info" : {
			"food_price" : "15900",
			"cash_price" : "10000",
			"card_price" : "5000",
			"coupon_price" : "900"
		}
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"org_pay_type"  :  "2",
		"order_pay_type"  :  "3"
	}
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (주문 금액)
> 주문 금액 변경 (From 상점 Client/App).
>

POST : https://domain/api/v1/order/update/foodprice


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|I-M | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|I-M | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"food_price" : "15900",
		"cash_price" : "10000",
		"card_price" : "5000",
		"coupon_price" : "900"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER FOODCOMPLETE UPDATE ] 주문 정보 변경 (음식 완료)
> 음식 완료 상태 변경 (From 상점 Client/App).
> story board : 상점 pc - 49page
>

POST : https://domain/api/v1/order/update/foodcomplete


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | x | 지사/지점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | food_complete_flag | x | (상점-음식완료) 물품(음식) 완료 여부 | 0: 미완성 1: 완성 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"food_complete_flag" : "1"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PICKUPTIME UPDATE ] 주문 정보 변경 (픽업 시간)
> 픽업 시간 변경 (From 상점 Client/App).
> story board : 상점 pc - 49page
>

POST : https://domain/api/v1/order/update/pickuptime


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | - |
|I | pickup_change_time | x | (픽업시간변경) 픽업변경시간 | 분단위 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"pickup_change_time" : "15"		
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (수행 상태)
> 수행 상태 변경 (From 지점/상점 Client/App).
> story board : 관리자 pc - 30page
>

POST : https://domain/api/v1/order/update/status


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | x | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | order_status | ㅇ | 현재 진행 상태 표시 | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"order_status" : "5"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (접수 취소)
> 접수 취소 (From 지점/상점 Client/App).
> story board : 관리자 pc - 31page
>

POST : https://domain/api/v1/order/update/ordercancel


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | order_status | ㅇ | 현재 진행 상태 표시 | 6: 배달 취소(정상) |
|I-M | cancel_cause | ㅇ | 취소 사유 | 0: 사유 없음<br>1: 상점 취소<br>2: 위치 불분명<br>3: 시간 지연<br>4: 고객 부재<br>5: 기사 부족<br>6: 기사 취소<br>7: 기타(직접입력) |
|I-M | cancel_text | x | 기타(직접입력) |  |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"order_status" : "6",
		"cancel_cause" : "4",
		"cancel_text" : ""
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (기사 배차 취소)
> 기사의 배차 취소 (From 라이더 App).
> story board :
>

POST : https://domain/api/v1/order/update/assigncancel

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | order_status | ㅇ | 현재 진행 상태 표시 | 1: 접수 |
|I-M | cancel_cause | ㅇ | 취소 사유 | 0: 사유 없음<br>1: 상점 취소<br>2: 위치 불분명<br>3: 시간 지연<br>4: 고객 부재<br>5: 기사 부족<br>6: 기사 취소<br>7: 기타(직접입력) |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "riderHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "9",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"order_status" : "6",
		"cancel_cause" : "6"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE UPDATE ] 주문 정보 변경 (지정배차)
> 지정 배차 (From 지점/상점 Client/App).
> story board : 관리자 pc - 38page
>

POST : https://domain/api/v1/order/update/orderassign


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "4",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"store_id" : "BP0001-SS0001",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"assign_type" : "1"			
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAYTYPE CHECK ] 지정 배차 수신 확인
> 지정 배차 수신 확인 (From 기사 App).
> story board :
>

POST : https://domain/api/v1/order/update/checkassign


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | assign_type | ㅇ | 배차 방식 | 2: 지정배차(=강제 배차) 확인 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "riderHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "3",
	"user_type" : "9",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"store_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"assign_type" : "2"			
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER RIDER SUPPORT PRICE ] 기사 지원금 수정
> 기사 지원금 수정
> story board : 관리자 pc - 25page
>

POST : https://domain/api/v1/order/update/supportprice


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | rider_support_price | ㅇ | 요금지원 | 삭제 시 '0' |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP00012020010118102201",
	"user_id" : "bpHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"store_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"rider_support_price" : "100"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER UPDATE: ADJUST CHARGE ] 요금조정 수정
> 요즘 조정 수정
>

POST : https://domain/api/v1/order/update/adjustcharge


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | store_id | ㅇ | 상점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | update_data | ㅇ | DATA | object |
|I-M | adjust_charge  | ㅇ |  (배달요금)요금조정 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "BP00012020010118102201",
	"user_id" : "bpHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"store_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"update_data" : {
		"adjustCharge" : "100"
	}
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAY CANCEL ] 결제취소
> 결제 취소 (From 기사 App).
> 카드 결제 취소만 가능
> story board : 기사 app - 41page
>

POST : https://domain/api/v1/order/update/paycancel

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | rider_id | ㅇ | 기사 account_id | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | pay_flag | ㅇ  | 결제 여부  | 0: 결제 전 |
|I | payed_price | x | 결제 완료 금액 | |
|I | rm_cash_price | x | 실제 결제 수단 중 복합의 현금 결제 금액 | |
|I | rm_card_price | x | 실제 결제 수단 중 복합의 카드 결제 금액 | |
|I | rm_coupon_price | x | 실제 결제 수단 중 복합의 상품권 결제 금액 | |
|I | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | real_pay_type | ㅇ | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | pay_time | ㅇ | 취소 일시 | yyyymmddhh24miss |
|I | card_approval_list | ㅇ | 신용카드 결제 승인 정보 | LIST |
|I-L | tran_type | ㅇ | 거래구분자 | credit : 신용승인, credit_cancel : 신용취소 |
|I-L | card_name | ㅇ | 결제한 카드사 | "신한카드" |
|I-L | total_amount | ㅇ | 총 결제 금액 | "50000" |
|I-L | result_code | ㅇ | 응답코드 | 0000: 정상,  |
|I-L | approval_num | ㅇ | 거래 승인번호 |  |
|I-L | org_approval_num | ㅇ | 취소 거래시 원승인번호 |  |
|I-L | approval_date | ㅇ | 거래 승인날짜 | "YYMMDDHHMMSSW" - 2004031215175 |
|I-L | acquirer_code | ㅇ | 매입사 코드 | "031" |
|I-L | acquirer_name | ㅇ | 매입사 명 | "신한카드" |
|I-L | merchant_num | ㅇ | 가맹점 번호 | "1234567890" |
|I-L | shop_tid | ㅇ | 결제된 가명점 TID | "0700081" |
|I-L | add_field | ㅇ | 추가필드 | 오더번호 "20200424115200ADA010" |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | - |

>  Request
```json
{
	"tr_id" : "RR00012020010118102201",
	"user_id" : "riderHong",
	"account_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "9",
	"branch_id" : "NG0000-LG0000-BB0000-BP0001",
	"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"pay_flag" : "0",
	"payed_price" : "50000",
	"order_pay_type" : "1",
	"real_pay_type" : "1",
	"pay_time" : "202004031215175",
	"card_approval_list" : [
		{
			"tran_type" : "credit_cancel",
			"card_name" : "신한카드",
			"total_amount" : "50000",
			"result_code" : "0000",
			"approval_num" : "12345678",
			"org_approval_num" : "",
			"approval_date" : "2004031215175",
			"acquirer_code" : "031",
			"acquirer_name" : "신한카드",
			"merchant_num" : "1234567890",
			"shop_tid" : "0700081",
			"add_field" : "20200424115200ADA010"
		}
	]
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER PAY COMPLETE] 재결제
> 결제 취소 (From 기사 App).
> story board : 기사 app - 41page
>

POST : https://domain/api/v1/order/update/paycomplete


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | x | 관리 지사/지점 account_id (기사 소속 지점) | |
|I | rider_id | ㅇ | 기사 ID | |
|I | shared_rider_flag | ㅇ | 공유지점 기사 여부 | 0: 소속 지점 기사<br>1: 공유 지점 기사 |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | pay_flag | ㅇ  | 결제 여부  | 1: 결제 |
|I | payed_price | x | 결제 완료 금액 | |
|I | rm_cash_price | x | 실제 결제 수단 중 복합의 현금 결제 금액 | |
|I | rm_card_price | x | 실제 결제 수단 중 복합의 카드 결제 금액 | |
|I | rm_coupon_price | x | 실제 결제 수단 중 복합의 상품권 결제 금액 | |
|I | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | real_pay_type | ㅇ | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정 |
|I | pay_time | ㅇ | 결제 일시 | yyyymmddhh24miss |
|I | card_approval_list | ㅇ | 신용카드 결제 승인 정보 | LIST |
|I-L | tran_type | ㅇ | 거래구분자 | credit : 신용승인, credit_cancel : 신용취소 |
|I-L | card_name | ㅇ | 결제한 카드사 | "신한카드" |
|I-L | total_amount | ㅇ | 총 결제 금액 | "50000" |
|I-L | result_code | ㅇ | 응답코드 | 0000: 정상,  |
|I-L | approval_num | ㅇ | 거래 승인번호 |  |
|I-L | org_approval_num | ㅇ | 취소 거래시 원승인번호 |  |
|I-L | approval_date | ㅇ | 거래 승인날짜 | "YYMMDDHHMMSSW" - 2004031215175 |
|I-L | acquirer_code | ㅇ | 매입사 코드 | "031" |
|I-L | acquirer_name | ㅇ | 매입사 명 | "신한카드" |
|I-L | merchant_num | ㅇ | 가맹점 번호 | "1234567890" |
|I-L | shop_tid | ㅇ | 결제된 가명점 TID | "0700081" |
|I-L | add_field | ㅇ | 추가필드 | 오더번호 "20200424115200ADA010" |
|I | location_x | ㅇ | 라이더 위치 X 좌표 | |
|I | location_y | ㅇ | 라이더 위치 Y 좌표 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "rider001",
	"account_id" : "BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "9",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"shared_rider_flag" : "0",
	"order_no" : "BP0001-SS000022710000BD123",
	"pay_flag" : "1",
	"payed_price" : "50000",
	"order_pay_type" : "1",
	"real_pay_type" : "1",
	"order_status" : "5",
	"pay_time" : "202004031215175",
	"card_approval_list" : [
		{
			"tran_type" : "credit",
			"card_name" : "신한카드",
			"total_amount" : "50000",
			"result_code" : "0000",
			"approval_num" : "12345678",
			"org_approval_num" : "",
			"approval_date" : "2004031215175",
			"acquirer_code" : "031",
			"acquirer_name" : "신한카드",
			"merchant_num" : "1234567890",
			"shop_tid" : "0700081",
			"add_field" : "20200424115200ADA010"
		}
	],
	"location_x" : "123.123456",
	"location_y" : "123.123457"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---
##### [ ORDER : ORDER NOTIFY CATCH CANCEL ] 기사 배정에 따른 타 기사 대기 콜 삭제 알림
> 주문 콜 기사 배정에 따른 타 기사의 대기 콜 삭제 알림  (To 기사App).
> WebSocket API

POST : https://domain/api/v1/order/notify/catchcancel


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | ㅇ | 지사/지점 account_id | |
|I | rider_id | ㅇ | 기사 ID | |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 2: 가배차<br>3: 배차완료 픽업 이동중(배정) |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|

>  Request
```json
{
	"tr_id" : "BP0001-SS00012020010118102201",
	"user_id" : "store001",
	"account_id" : "BP0001-SS0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"shared_id" : "BP0002",
	"rider_id" : "BP0001-RR0001",
	"order_no" : "BP0001-SS000022710000BD123",
	"order_status" : "3"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```
---
##### [ ORDER : ORDER CONFIRM ] 기사 배정요청
> 배정기사의 주문음식 배정 요청  (From 기사App).
>
>>>1. shared_branch_id 삭제  

POST : https://domain/api/v1/order/confirm


| 구분| 파라메타| 필수 | 설명 |value |
:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | x | 관리 지사/지점 account_id (기사 소속 지점) | |
|I | rider_id | ㅇ | 기사 ID | |
|I | shared_rider_flag | ㅇ | 공유지점 기사 여부 | 0: 소속 지점 기사<br>1: 공유 지점 기사 |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | order_status | ㅇ | 현재 진행 상태 표시 | tmp_assign_flag 설정 시 2: 가배차 아닐 경우 3: 배차완료 픽업 이동중(배정) |
|I | location_x | ㅇ | 라이더 위치 X 좌표 | |
|I | location_y | ㅇ | 라이더 위치 Y 좌표 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "rider001",
	"account_id" : "BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"shared_rider_flag" : "0",
	"order_no" : "BP0001-SS000022710000BD123",
	"order_status" : "3",
	"location_x" : "123.123456",
	"location_y" : "123.123457"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---
##### [ ORDER : ORDER PICKUP]  기사 픽업 완료
> 배정기사의 주문음식 픽업 완료 요청  (From 기사App).
>
>>> 1. shared_branch_id 삭제  

POST : https://domain/api/v1/order/pickup


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | x | 관리 지사/지점 account_id (기사 소속 지점) | |
|I | rider_id | ㅇ | 기사 ID | |
|I | shared_rider_flag | ㅇ | 공유지점 기사 여부 | 0: 소속 지점 기사<br>1: 공유 지점 기사 |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br> |
|I | location_x | ㅇ | 라이더 위치 X 좌표 | |
|I | location_y | ㅇ | 라이더 위치 Y 좌표 | |
|O | result_cd  | o | 결과코드 |-|
|O | result_message | o | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "rider001",
	"account_id" : "BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"shared_rider_flag" : "0",
	"order_no" : "BP0001-SS000022710000BD123",
	"order_status" : "4",
	"location_x" : "123.123456",
	"location_y" : "123.123457"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER COMPLETE]  기사 배송완료
> 기사의 배송 완료 요청  (From 기사App).
>
>>> 1. shared_branch_id 삭제  

POST : https://domain/api/v1/order/complete


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id | x | 관리 지사/지점 account_id (기사 소속 지점) | |
|I | rider_id | ㅇ | 기사 ID | |
|I | shared_rider_flag | ㅇ | 공유지점 기사 여부 | 0: 소속 지점 기사<br>1: 공유 지점 기사 |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|I | pay_flag | ㅇ  | 결제 여부  | 0: 결제 전<br>1: 결제 |
|I | payed_price | x | 결제 완료 금액 | |
|I | rm_cash_price | x | 실제 결제 수단 중 복합의 현금 결제 금액 | |
|I | rm_card_price | x | 실제 결제 수단 중 복합의 카드 결제 금액 | |
|I | rm_coupon_price | x | 실제 결제 수단 중 복합의 상품권 결제 금액 | |
|I | order_pay_type | ㅇ | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | real_pay_type | ㅇ | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|I | order_status | ㅇ | 현재 진행 상태 표시 | 5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정 |
|I | pay_time | ㅇ | 결제 일시 | yyyymmddhh24miss |
|I | card_approval_list | ㅇ | 신용카드 결제 승인 정보 | LIST |
|I-L | tran_type | ㅇ | 거래구분자 | credit : 신용승인, credit_cancel : 신용취소 |
|I-L | card_name | ㅇ | 결제한 카드사 | "신한카드" |
|I-L | total_amount | ㅇ | 총 결제 금액 | "50000" |
|I-L | result_code | ㅇ | 응답코드 | 0000: 정상,  |
|I-L | approval_num | ㅇ | 거래 승인번호 |  |
|I-L | org_approval_num | ㅇ | 취소 거래시 원승인번호 |  |
|I-L | approval_date | ㅇ | 거래 승인날짜 | "YYMMDDHHMMSSW" - 2004031215175 |
|I-L | acquirer_code | ㅇ | 매입사 코드 | "031" |
|I-L | acquirer_name | ㅇ | 매입사 명 | "신한카드" |
|I-L | merchant_num | ㅇ | 가맹점 번호 | "1234567890" |
|I-L | shop_tid | ㅇ | 결제된 가명점 TID | "0700081" |
|I-L | add_field | ㅇ | 추가필드 | 오더번호 "20200424115200ADA010" |
|I | location_x | ㅇ | 라이더 위치 X 좌표 | |
|I | location_y | ㅇ | 라이더 위치 Y 좌표 | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "rider001",
	"account_id" : "BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id" : "BP0001",
	"rider_id" : "BP0001-RR0001",
	"shared_rider_flag" : "0",
	"order_no" : "BP0001-SS000022710000BD123",
	"pay_flag" : "1",
	"payed_price" : "50000",
	"rm_cash_price" : "0",
	"rm_card_price" : "0",
	"rm_coupon_price" : "0",
	"order_pay_type" : "0",
	"real_pay_type" : "0",
	"order_status" : "5",
	"pay_time" : "202004031215175",
	"card_approval_list" : [
		{
			"tran_type" : "credit",
			"card_name" : "신한카드",
			"total_amount" : "18000",
			"result_code" : "0000",
			"approval_num" : "12345678",
			"org_approval_num" : "",
			"approval_date" : "2004031215175",
			"acquirer_code" : "031",
			"acquirer_name" : "신한카드",
			"merchant_num" : "1234567890",
			"shop_tid" : "0700081",
			"add_field" : "20200424115200ADA010"
		}
	],
	"location_x" : "123.123456",
	"location_y" : "123.123457"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : ""
}
```

---

##### [ ORDER : ORDER CARD APPROVAL LIST] 카드 승인 정보 상세 보기
> 카드 승인 정보 상세 보기.  
> table : DDT_CARD_APPROVAL_HISTORY_YY  
> story board : 관리자 pc - 25  

POST : https://domain/api/v1/order/card_approval_list


| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | shared_branch_id | x | 공유 지사/지점 account_id | 공유 지사/지점 ID |
|I | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | -|
|O | card_approval_list | ㅇ | 신용카드 결제 승인 정보 | LIST |
|O-L | approval_date | ㅇ | 거래 승인날짜 | "YYMMDDHHMMSSW" - 2004031215175 |
|O-L | card_name | ㅇ | 결제한 카드사 | "신한카드" |
|O-L | approval_num | ㅇ | 거래 승인번호 |  |
|O-L | tran_type | ㅇ | 거래구분자 | 승인취소 : credit_cancel / 승인 : credit |
|O-L | total_amount | ㅇ | 총 결제금액 |  |
|O-L | result_code | ㅇ | 응답코드 | 0000: 정상, 나머지: 실패  |

>  Request
```json
{
	"tr_id" : "BP0001-RR00012020010118102201",
	"user_id" : "rider001",
	"account_id" : "BP0001-RR0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"shared_branch_id" : "",
	"order_no" : "BP0001-SS000022710000BD123"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"card_approval_list" : [
			{
				"approval_date" : "2004031215175",
				"card_name" : "신한카드",
				"approval_num" : "12345678",
				"tran_type" : "credit",
				"total_amount" : "10000",
				"result_code" : "0000"
			},
			{
				"approval_date" : "20040312151750",
				"card_name" : "신한카드",
				"approval_num" : "12345678",
				"tran_type" : "credit_cancel",
				"total_amount" : "5000",
				"result_code" : "0000"
			}
		]
	}
}
```

---
##### [ ORDER : ORDER STATUS ] 주문 현황 조회
> 주문 현황 정보 조회 (From 상점/지사/기사 Client/App).
> story board : 관리자 pc - 20, 32page
> 엑셀다운로드 : https://domain/api/v1/order/status/excel (request : 기존과 동일, response 성공 시 : excelfile(http stream) / 실패 시 : 기존과 동일)
>
POST : https://domain/api/v1/order/status

| 구분| 파라메타| 필수 | 설명 |value |
|:--------|:--------|:-------:|:--------------------|:------|
|I | branch_id_list | ㅇ | LIST | 기본 값은 소속 지점의 branch_id |
|I-L | branch_id | x | 관리 지사/지점 account_id (소속 지점) | |
|I | order_status_list | ㅇ | LIST | 진행 상태 |
|I-L | order_status | x | 현재 진행 상태 (없을 경우 전체) | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|I | select_info | ㅇ | 검색 조건 | object |
|I-M | start_date | ㅇ | 조회 기간 | 있을 경우 과거관제  |
|I-M | end_date | ㅇ | 조회 기간 | 있을 경우 과거관제 |
|I-M | pay_flag | ㅇ | 결제 여부 | 없을 경우 전체<br> 0: 결제 전, 1 : 결제 |
|I-M | pickup_change_flag | ㅇ | 픽업 시간 변경 | 없을 경우 전체, 1 : 픽업 시간 변경 건 |
|I-M | select_type | ㅇ | 검색 조건 | 0:전체, 1:기사명, 2: 상점명, 3: 오더번호, 4: 출발지명, 5: 도착지명, 6: 결제수단, 7: 지정배차, 8: 배달전화, 9: 취소사유, 10: 관리지점, 11: 관제제점, 12: 결제금액, 13: rider_id |
|I-M | select_keyword | ㅇ | 키워드 | 없을 경우 전체 |
|I | page_no | - | 최초 미입력시 1   | 0: 전체 |
|I | count_per_page| ㅇ | 페이지당 출력할 카운트  | |
|O | result_cd  | ㅇ | 결과코드 |-|
|O | result_message | ㅇ | 에러 메시지, 정상일 경우 공란 | |
|O | data | ㅇ | DATA | object|
|O | total_count  | ㅇ | 결과값 총 카운트 | |
|O | status_count_data | ㅇ | 상태별 총 카운트 | object |
|O-M | reservation_count  | ㅇ | 예약 카운트 | |
|O-M | accept_count  | ㅇ | 접수 카운트 | |
|O-M | confirm_count  | ㅇ | 배차 카운트 | |
|O-M | pickup_count  | ㅇ | 픽업 카운트 | |
|O-M | complete_count  | ㅇ | 완료 카운트 | |
|O-M | cancel_count  | ㅇ | 취소 카운트 | |
|O | page_no | ㅇ | 입력한 페이지 번호 |  |
|O | count_per_page | ㅇ | 페이지당 출력할 카운트 |  |
|O | order_list | ㅇ | DATA | LIST |
|O-L | order_no | ㅇ | 배달 대행 요청 ID(운송장번호) - 오더번호 | MMDDHH24MISS+accountid+C/A/L+영수랜덤5자리 |
|O-L | link_order_no | x | 외부 연동 주문 번호 | (스토리보드)주문 번호로 표시 |
|O-L | link_order_ch | x | 외부 연동 주문 채널 | (스토리보드)주문 채널로 표시 |
|O-L | branch_id | x | 지점 account_id | 접수 상점 관리 지점 명 |
|O-L | branch_user_id  | x | 지점 login_id |  |
|O-L | branch_name | x | 지점 이름 | 접수 상점 관리 지점 명 |
|O-L | store_id | x | 상점 account_id | |
|O-L | store_user_id | x | 상점 계정 LOGINID | |
|O-L | store_name | ㅇ | 상점 이름 | |
|O-L | store_dong_info | x | 상점 동 정보 | |
|O-L | store_location_x | x | 상점 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|O-L | store_location_y | x | 상점 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|O-L | rider_id | x | 기사 ID | |
|O-L | rider_user_id | x | 기사 계정 LOGIN ID | |
|O-L | rider_name | x | 배차된 배달기사 이름 | |
|O-L | orderer_tel | x | 주문고객 연락처 | |
|O-L | order_note | x | 배달 시 필요한 정보 또는 유의사항 기재 | |
|O-L | delivery_new_addr | x | 배달지 신주소 | |
|O-L | delivery_old_addr | x | 배달지 구주소 | |
|O-L | delivery_input_addr | x | 배달지 입력주소 | |
|O-L | delivery_dong_info | x | 배달지 동 정보 | |
|O-L | delivery_location_x | x | 배달 도착지 위치 X 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|O-L | delivery_location_y | x | 배달 도착지 위치 Y 좌표 | 소수점 위3자리, 아래 6자리<br>예: AAA.BBBBBB |
|O-L | assign_type | x | 배차 방식 | 0: 전투 배차(기본) 1: 지정 배차(=강제 배차) 미확인 2: 지정배차(=강제 배차) 확인 |
|O-L | order_status | x | 현재 진행 상태 표시 | 0: 배달 예약 대기<br>1: 접수<br>2: 가배차<br>3: 배차완료 픽업 이동중(배정)<br>4: 픽업완료 및 배달중<br>5: 배달 완료(현금/카드결제)<br>6: 배달 취소(정상)<br>7: 배달 실패(비정상)<br>8: 배달 완료 (카드 결제취소)-미정<br>9: 재접수 |
|O-L | status_change_time | x | 주문 변경 시간 |  |
|O-L | accept_lap_time | x | 접수 경과 시간 | HH:MM |
|O-L | status_lap_time | x | 상태 경과 시간 | HH:MM |
|O-L | pickup_remain_min | x | 픽업 남은 시간 | 분 |
|O-L | store_company_crn | ㅇ | (상점) 사업자 번호 | |
|O-L | cancel_cause | x | 배달 취소 원인 | 0: 해당사항없음<br>1: 고객 거부 |
|O-L | real_pay_type | x | 실제 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|O-L | distance | x | 배달지까지의 거리(단위 : km) | |
|O-L | pay_flag | x | 결제 여부 | 0: 결제 전<br>1 : 결제 |
|O-L | order_pay_type | x | 주문시 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|O-L | org_pay_type | x | 주문시 이전 고객 결제 방식 | 0: 선불<br>1: 후불카드<br>2: 후불현금<br>3: 복합(현금,카드,상품권)<br>4: 상품권(쿠폰포함) |
|O-L | food_price | x | (오더접수) 상품가액 (물품 구매 금액) | |
|O-L | cash_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|O-L | card_price | x | (오더접수) 결제 수단 중 복합의 현금 결제 금액 | |
|O-L | coupon_price | x | (오더접수) 결제 수단 중 복합의 상품권 결제 금액 | |
|O-L | payed_price | x | 결제 완료 금액 | |
|O-L | delivery_fees | x |(관리자-배송요금) 수수료 합계 (기사수수료+관리수수료+관제수수료+요금지원) |  |
|O-L | rider_fees | x | 배달기사 수수료 | 기본요금 + 추가거리 할증요금 |
|O-L | manage_fees | x | 관리 수수료 | |
|O-L | control_fees | x | 관제 수수료(=공유지점 수수료) | |
|O-L | basic_charge | x | 기본요금 | |
|O-L | holiday_extra_charge | x | 휴무일 할증요금 | |
|O-L | area_extra_charge | x | 특정주소 할증요금 | |
|O-L | weather_extra_charge | x | 기상 할증요금 | 0: 기상할증OFF, 0>기상할증 금액 |
|O-L | total_extra_charge | x | (배송비) 총할증금액 (기상할증금액+휴무할증금액+일반할증1~5+기타할증금액+추가거리할증) | |
|O-L | total_delivery_charge | x | (배송비) 총 배송비(배송비+총할증금액) | |
|O-L | pickup_req_time | x | 배차지연 설정 시간 초과 지연 시간 (현재 시간 - 배차 지연 설정 시간) - 표시(hh:mm) | yyyymmddhh24miss |
|O-L | pickup_change_time | x | (픽업시간변경) 픽업변경시간 | 분단위 |
|O-L | store_tel1 | x | 상점 연락처1 | |
|O-L | rider_support_price | x | 기사 지원금 | |
|O-L | store_order_type | x | 상점 주문 유형 | 상점타입 리스트 참조 |
|O-L | rider_tel | x | 배차된 배달기사 무선 연락처 | |
|O-L | rider_branch_id | x | 관제 지점 ID (기사 소속 지점 ID) | |
|O-L | rider_branch_name | x | 관제 지점 이름 (기사 소속 지점 이름) | |
|O-L | reservation_time | x | 배달 예약 시간 - 표시(hh:mm) | yyyymmddhh24miss |
|O-L | order_time | x | 주문 접수시간 - 표시(hh:mm) | yyyymmddhh24miss |
|O-L | assign_time | x | 배차성공시간(기사배정) - 표시(hh:mm) | yyyymmddhh24miss |
|O-L | pickup_time | x | 라이터가 물품 픽업한 시간(픽업시간) - 표시(hh:mm) | yyyymmddhh24miss |
|O-L | success_time | x | 배달완료시간 | yyyymmddhh24miss |
|O-L | cancel_time | x | 배달취소시간 | yyyymmddhh24miss |
|O-L | canceler | x | 배달 취소자 지점/ 상점/ 기사 + account_id + ACCOUNTNAME | |
|O-L | food_complete_flag | x | (상점-음식완료) 물품(음식) 완료 여부 | 0: 미완성 1: 완성 |
|O-L | food_complete_time | x | (상점-음식완료) 물품(음식) 완료 시간 | |
|O-L | card_approval_no | x | 신용카드 결제 승인번호 | 필수항목 |
|O-L | data_update_date | x | 데이터 수정 일자 | yyyymmddhh24miss |
|O-L | rider_delay_duration | x | (관리자-지점) 기본배차지연시간 (분단위) - 오더접수시 지점 설정에서 입력 : 해당 상점에서 픽업요청시간 입력시 해당 시간 이상으로 기록할 수 있음. |  |
|O-L | rider_sort_flag | x | 기사앱 오더리스트 정렬 기준값 | 0: 시간순(기본값), 1: 긴급순, 2: 거리순 |
|O-L | rider_sort_count | x | 기사앱 오더리스트 출력 카운트 제한 값 | 0: 제한 없음, 1 ~ : 해당 카운트 만큼 출력 |

>  Request
```json
{
	"tr_id" : "BB0001-BP00012020010118102201",
	"user_id" : "branch001",
	"account_id" : "NG0000-LG0000-BB0000-BP0001",
	"cast_type" : "0",
	"command_type" : "0",
	"user_type" : "6",
	"branch_id_list" : [
		"BP0001",
		"BP0002",
		"BP0003"		
	],
	"order_status_list" : [
		"1",
		"2"
	],
	"select_info" : {
		"start_date" :  "",
		"end_date" : "",
		"pay_flag" : "",
		"select_type" : "0",
		"select_keyword" : ""
	},
	"page_no" : "1",
	"count_per_page" : "10"
}
```

>  Response
```json
{
	"result_cd" : "2001",
	"result_message" : "",
	"data" : {
		"total_count" : "1",
		"status_count_data" :
			{
				"reservation_count" : "0",
				"accept_count" : "1",
				"confirm_count" : "0",
				"pickup_count" : "0",
				"complete_count" : "0",
				"cancel_count" : "0"
			},
		"page_no" : "1",
		"count_per_page" : "10",
		"order_list" : [
			{
				"order_no" : "BP0001-SS000022710000BD123",
				"link_order_no" : "",
				"link_order_ch" : "",
				"branch_id" : "NG0000-LG0000-BB0000-BP0001",
				"branch_user_id" : "bpHong",
				"branch_name" : "지점이름",
				"store_id" : "NG0000-LG0000-BB0000-BP0001-SS0001",
				"store_name" : "상점이름",
				"store_user_id" : "storeHong",
				"store_dong_info" : "죽전동",
				"store_location_x" : "123.123456",
				"store_location_y" : "123.123457",
				"rider_id" : "NG0000-LG0000-BB0000-BP0001-RR0001",
				"rider_user_id" : "riderHong",
				"rider_name" : "라이더",
				"orderer_tel" : "01022222222",
				"order_note" : "맛있게부탁합니다",
				"delivery_new_addr" : "경기도 용인시 수지구 죽전길 2",
				"delivery_old_addr" : "경기도 용인시 수지구 죽전동 456",
				"delivery_input_addr" : "다우기술 6층",
				"delivery_dong_info" : "죽전동",
				"delivery_location_x" : "123.123459",
				"delivery_location_y" : "123.123458",
				"assign_type" : "0",
				"order_status" : "1",
				"status_change_time" : "20200101182022",
				"accept_lap_time" : "00:20",
				"status_lap_time" : "00:20",
				"pickup_remain_min" : "120",
				"store_company_crn" : "123-12-1234",
				"cancel_cause" : "0",
				"real_pay_type" : "0",
				"distance" : "1.5",
				"pay_flag" : "0",
				"order_pay_type" : "1",
				"org_pay_type" : "1",
				"food_price" : "20000",
				"cash_price" : "0",
				"card_price" : "20000",
				"coupon_price" : "0",
				"payed_price" : "20000",
				"delivery_fees" : "3300",
				"rider_fees" : "2700",
				"manage_fees" : "150",
				"control_fees" : "150",		
				"holiday_extra_charge" : "0",
				"area_extra_charge" : "0",
				"weather_extra_charge" : "0",
				"total_extra_charge" : "300",
				"total_delivery_charge" : "3000",
				"pickup_req_time" : "20200101182022",
				"pickup_change_time" : "15",
				"store_tel1" : "01012341234",
				"rider_support_price" : "1000",
				"store_order_type" : "0",
				"rider_tel" : "01044444444",
				"rider_branch_id" : "NG0000-LG0000-BB0000-BP0001",
				"rider_branch_name" : "다우지점",
				"reservation_time" : "",
				"order_time" : "20200101181022",
				"assign_time" : "20200101181222",
				"pickup_time" : "20200101181622",
				"success_time" : "20200101182922",
				"cancel_time" : "",
				"canceler" : "",
				"food_complete_flag" : "0",
				"food_complete_time" : "",
				"card_approval_no" : "01230898",
				"data_update_date" : "20200316161100",
				"rider_delay_duration" : "15",
				"rider_sort_flag" : "0",
				"rider_sort_count" : "0"
			}
		]
	}
}
```
