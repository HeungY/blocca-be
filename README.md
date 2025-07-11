# 🌈 Blocca 보드게임 서비스
<p align="center">
  <img width="768" height="512" alt="blocca" src="https://github.com/user-attachments/assets/3f0ee8dd-4f9c-4538-b10c-6731409cc6be" />
</p>

* 서비스 주소 : [🏳️‍🌈 Blocca 🏳️‍🌈](https://blocca.run)

<br/>

## 🌱 소개
블로카는 두 명의 플레이어가 빨강, 노랑, 초록, 파랑 네 개의 색 주사위와 <br/>
흰색 주사위 두 개의 조합을 통해 보드의 색상을 많이 체크하면 승리하는 게임입니다.

<br/>


## 개요
심심할때 친구와 쉽고 재밌고 빠르게 즐길 수 있는 게임을 만들자는 생각으로 블로카를 개발했습니다. <br/>
별도의 설치 없이 사용자가 간편하게 즐길 수 있도록 웹을 통해 서비스 하였습니다.

<br/>

## 게임 룰

### 게임 진행
- A와 B가 서로 턴을 번갈아가며 진행합니다.
- A의 턴. A가 주사위를 모두 굴립니다. ( 흰색 주사위 2개, 빨강, 노랑, 초록, 파랑 각각 1개씩 )
- 색상 라인의 왼쪽 숫자는 선택할 수 없습니다. ( ex. 빨강 3을 선택했는데, 다음에 빨강 2 선택 불가 )
- A 와 B 모두 흰색 주사위 2개의 합한 값을 원하는 색상의 숫자에 체크합니다. ( 체크를 안 할 수도 있음 )
- A의 턴이기 때문에 A 는 흰색 주사위 둘 중의 하나의 숫자와 색상 주사위의 숫자를 더해서 해당 색상의 숫자에 체크합니다. ( 체크를 안 할  수도 있음 )
- B의 턴으로 넘어가서 반복합니다.

<br/>


### 게임 종료
- A의 턴인데 A가 흰색 주사위 두개 체크와 흰색 + 색상 주사위의 체크를 하지 않았을 시, 실패 횟수가 1회 증가합니다.
- 보드의 마지막 숫자는 왼쪽에 체크된 숫자가 5개 이상일 시, 체크할 수 있습니다. 체크시 보드의 해당 색상 라인이 잠궈집니다. (라인을 잠구면 해당 색상 체크 갯수 +1)
- A 또는 B의 실패 횟수가 4회 일시, 게임에서 잠긴 색상 라인이 2개 이상일시, 게임이 종료됩니다.

<br/>


### 점수
- 라인별 체크 갯수에 따라 점수를 내고, 모든 색상을 합친 값 - 실패 횟수 x 5 를 통해 점수를 계산합니다.

| 체크 | 1 | 2 | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 | 11 | 12 |
|------|---|---|----|----|----|----|----|----|----|----|----|----|
| 점수 | 1 | 3 | 6  | 10 | 15 | 21 | 28 | 36 | 45 | 55 | 66 | 78 |

ex. 빨강 3개, 노랑 2개, 초록 5개, 파랑 7개, 실패 2회 = (6 + 3 + 15 + 28) - 10 = 42점

<br/>


## 이용 방법

<p align="center">
  <img width="768" height="512" alt="스크린샷 2025-07-11 오후 1 02 10" src="https://github.com/user-attachments/assets/00fa8f31-3333-4350-9e80-b991cdde7658" />
</p>

### 메인 화면
- 원하는 닉네임 입력 후 방 만들기 버튼을 통해 게임 방 개설
- 닉네임 입력, 방 코드를 입력 후 방 입장 버튼을 통해 게임 방 입장

<br/>

<p align="center">
  <img width="768" height="512" alt="스크린샷 2025-07-08 오후 5 57 15" src="https://github.com/user-attachments/assets/4d10dca6-96d0-4311-bb3a-9de3d4883096" />
</p>

### 보드 화면
- 오른쪽 상단 방 코드. 방 코드를 상대방에게 알려주면 상대방이 방 입장 가능
- 왼쪽 상단 상대편 보드, 왼쪽 하단 내 보드. ( 이렇게 구성한 이유는 모바일에서 가로모드로 볼 경우 주사위 굴리고 체크하기가 편리합니다. )
- 내 차례일 경우 "[닉네임]님의 차례입니다. 주사위를 굴려주세요." 표시. 오른쪽 아래 주사위 굴리기 버튼을 통해 주사위를 굴려주세요.
- 흰색 주사위 두개의 합을 선택합니다. 보드에서 원하는 색상의 숫자를 선택 후 오른쪽 아래 선택 완료 버튼을 눌러주세요. ( 선택한 숫자를 다시 선택하면 선택이 해제됩니다. )
- 상대방이 선택을 완료하지 않았다면 기다려 달라는 메시지가 표시됩니다.
- 흰색 주사위 체크 단계를 모두 넘어갔다면 주사위를 굴린 유저의 색상 선택 단계가 됩니다. 흰색 주사위 + 색상 주사위의 합을 해당 색상의 숫자에 체크해주세요. 흰색 체크 단계, 색상 체크 단계를 모두 건너뛰면 실패 횟수가 증가합니다.
- 모두 완료했다면 턴 바뀜 알림이 뜨며 다음 차례를 안내합니다. 반복해서 진행해주시면 됩니다.

<br/>

<p align="center">
  <img width="768" height="512" alt="스크린샷 2025-07-11 오후 1 49 09" src="https://github.com/user-attachments/assets/84cd2e09-4b4a-45b8-8bc4-3b8db7a3146f" />
</p>

- 라인이 잠길시, 모두에게 팝업 메세지가 표시되며 다음 단계부터 해당 색상 선택이 불가능합니다.
- 게임 종료시 결과 팝업이 표시됩니다.

<br/>


## 개발 환경
- 개발 인원 : 1인 개발
- 개발 기간 : 2025.05 - 배포 후 최적화 진행중

<br/>
<br/>

### 사용 스택
#### Frontend
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)

#### Backend
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-30B1FF?style=for-the-badge&logo=websocket&logoColor=white)
![STOMP](https://img.shields.io/badge/STOMP-FF6F00?style=for-the-badge&logo=stomp-protocol&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-FF8F00?style=for-the-badge&logo=java&logoColor=white)

#### DevOps
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Vercel](https://img.shields.io/badge/Vercel-000000?style=for-the-badge&logo=vercel&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![nginx](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white)

<br/>

### 프로젝트 구조

<p align="center">
  <img width="768" height="512" alt="스크린샷 2025-07-11 오전 10 59 48" src="https://github.com/user-attachments/assets/7f1d0b5a-395a-435b-9a5b-6f1918112e85" />
</p>


