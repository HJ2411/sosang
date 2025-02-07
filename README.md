# 🚀 소상링

✅ **소상공인들이 자신의 가게를 등록하고 홍보할 수 있도록 지원**  
✅ **사용자가 지도 기반으로 가게 정보를 쉽게 탐색 가능**  
✅ **가게에 대한 리뷰와 평점 제공하여 신뢰도 확보**  

소상공인들을 위한 맞춤형 가게 정보 제공 및 리뷰 시스템을 갖춘 **Java 기반 데스크톱 애플리케이션**을 사용해보세요!

---

## 📌 프로젝트 개요
**소상링**은 소상공인들이 자신의 가게를 등록하고 사용자들과 소통할 수 있도록 돕는 **Java 기반 GUI 애플리케이션**입니다.  
사용자는 지도에서 원하는 가게를 검색하고, 리뷰를 남기며, 관심 가게를 즐겨찾기에 추가할 수 있습니다.  

**🛠 주요 특징**
- **소상공인 맞춤형 가게 관리 시스템** (등록, 수정, 삭제 기능)  
- **사용자 리뷰 및 별점 시스템**을 통한 신뢰도 확보  
- **Google Maps API** 활용한 위치 기반 검색 기능  
- **MariaDB 기반 데이터베이스 연동**  
- **데스크톱 GUI 환경**에서 동작  

<br/>

💻 **100% Java 기반 애플리케이션**  
- 웹 기술 (HTML, JavaScript 등) 없이 **순수 Java**로 GUI와 기능을 구현  
- **Swing & AWT**를 활용한 데스크톱 애플리케이션 개발  
- **JDBC**를 활용하여 MariaDB와 직접 연결, SQL 기반 데이터 관리  
- 웹이 아닌 **독립 실행형 애플리케이션**으로 제작하여 설치형 프로그램 형태로 제공

---
## ❄️ 프로젝트 기간

 **2025년 1월 16일 ~ 2025년 1월 24일 (D+8, 8일)**

---

## 👨‍💻 기술 스택 (Tech Stack)
| 기술 | 설명 |
|------|------|
| **Java (JDK 17)** | 애플리케이션 핵심 로직 구현 |
| **Swing (JFrame, JPanel, JTable 등)** | GUI 인터페이스 개발 |
| **MariaDB** | 데이터베이스 저장 및 관리 |
| **JDBC (Java Database Connectivity)** | Java와 MariaDB 간 데이터 연동 |
| **Google Maps API** | 지도 검색 기능 |
| **Git & GitHub** | 형상 관리 및 협업 |

---

## 🙋‍♂️ 팀원 소개

| 이름  | 개인 깃허브                                 | 담당 역할 및 기능                                                                                                                 |
|-----| ------------------------------------------- |----------------------------------------------------------------------------------------------------------------------------|
| 이영주 | [sensato437](https://github.com/sensato437) | Google Maps API 적용 <br/>Database 구성  |
| 엄태정 | [TaeJeong-Eom](https://github.com/TaeJeong-Eom) | 댓글 및 평가하기 기능 <br/> GUI 디자인(로그인 페이지, 회원가입 페이지, 관심가게목록, 댓글창)    |
| 김희진 | [HJ2411](https://github.com/HJ2411)     | 로그인, 회원가입 기능 <br/> 네비게이터 기능(뒤로가기, 바로가기) <br/> 마이페이지 기능(개인정보 수정, 프로필 이미지 삽입) <br/> 좋아요 기능      |
| 신혜서 | [tlsgptj](https://github.com/tlsgptj)     | 검색 기능 <br/> GUI 디자인(메인페이지, 상품목록 가져오기 |

---

## 📌 **주요 기능 (Key Features)**  

| 기능 | 설명 |
|------|------|
| 🔐 **회원 관리** | 로그인, 회원가입, 사용자 구분 (일반 사용자/판매자) |
| 🏪 **가게 정보** | 가게 등록, 수정, 삭제, 검색 기능 |
| ⭐ **리뷰 시스템** | 사용자가 리뷰 작성 및 평점 등록 (별점 1~5) |
| ❤️ **관심 가게 (즐겨찾기)** | 하트(♥) 버튼으로 가게 즐겨찾기 추가/삭제 |
| 📢 **공지사항 시스템** | 공지 등록 및 조회 |
| 🗂 **데이터베이스 연동** | MariaDB와 연결하여 CRUD (Create, Read, Update, Delete) 구현 |

---

## 📌 **기능 상세 설명**  

#### 🔐 **회원 관리**
- 사용자는 회원가입 후 로그인 가능  
- 판매자와 일반 사용자를 구분하여 다른 기능 제공  
- 비밀번호 암호화 및 로그인 보안 강화

#### 🏪 **가게 정보**
- 판매자는 자신의 가게를 등록하고 수정할 수 있음  
- 사용자는 등록된 가게를 검색하고 정보 확인 가능  
- 가게의 영업시간, 위치, 업종, 사진 등을 표시  

#### ⭐ **리뷰 시스템**
- 사용자들이 가게에 대한 리뷰를 작성하고 별점 부여 가능  
- 리뷰를 통해 다른 사용자들이 가게에 대한 신뢰도를 평가할 수 있음  
- 리뷰 수정 및 삭제 기능 제공  

#### ❤️ **관심 가게 (즐겨찾기)**
- 하트(♥) 버튼을 눌러 관심 있는 가게를 즐겨찾기 추가  
- 다시 누르면 즐겨찾기에서 삭제  
- 데이터베이스와 연동하여 사용자의 관심 목록 유지  

#### 📢 **공지사항 시스템**
- 관리자가 앱 공지사항을 등록 및 수정 가능  
- 사용자는 공지사항을 통해 앱 업데이트 및 이벤트 확인 가능  

#### 🗂 **데이터베이스 연동**
- `MariaDB`를 사용하여 회원, 가게, 리뷰 등의 데이터 관리  
- `JDBC`를 활용한 `CRUD` (Create, Read, Update, Delete) 기능 구현  
- SQL 쿼리를 최적화하여 성능 개선  

---

<details>
    <summary> 프로젝트 실행해보기🚀 </summary>
    <div>

## 🚀 프로젝트 실행 방법 (Setup & Run)
### 1️⃣ **필요한 환경 설정**
- **Java JDK 17 이상 설치**
- **MariaDB 설치 및 데이터베이스 설정**
- **Google Maps API 키 발급 (선택 사항)**

### 2️⃣ **데이터베이스 설정**
```sql
CREATE DATABASE App_schema;
USE App_schema;
-- 테이블 생성 쿼리
START TRANSACTION;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS ReviewComments;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS InterestList;
DROP TABLE IF EXISTS Notice;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Store;
DROP TABLE IF EXISTS Seller;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS BlackMember;
DROP TABLE IF EXISTS Login;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Industry;

DROP TRIGGER IF EXISTS MemberInsert;

-- Member 테이블 생성
CREATE TABLE Member (
    MemberID VARCHAR(13) NOT NULL,
    Pwd VARCHAR(14) NULL,
    Name VARCHAR(26) NULL,
    Nick VARCHAR(13) NULL,
    Birth DATE NULL,
    Email VARCHAR(100) NULL,
    MemberImage VARCHAR(100) NULL,
    CONSTRAINT PK_Member PRIMARY KEY (MemberID)
);

-- Industry 테이블 생성
CREATE TABLE Industry (
    IndustryNo INT(2) NOT NULL,
    IndustryName VARCHAR(10) NULL,
    CONSTRAINT PK_INDUSTRY PRIMARY KEY (IndustryNo)
);

-- Seller 테이블 생성
CREATE TABLE Seller (
    SellerNo INT(10) NOT NULL,
    StorePhone VARCHAR(13) NULL,
    MemberID VARCHAR(13) NOT NULL,
    CONSTRAINT PK_Seller PRIMARY KEY (SellerNo),
    CONSTRAINT FK_Member_TO_Seller FOREIGN KEY (MemberID) REFERENCES Member (MemberID)
);

-- Store 테이블 생성
CREATE TABLE Store (
    StoreNo INT NOT NULL AUTO_INCREMENT,
    StoreName VARCHAR(40) NULL,
    OpenHours VARCHAR(100) NULL,
    LOC VARCHAR(100) NULL,
    StoreImage VARCHAR(100) NULL,
    SellerNo INT(10) NOT NULL,
    IndustryNo INT(2) NOT NULL,
    CONSTRAINT PK_Store PRIMARY KEY (StoreNo),
    CONSTRAINT FK_Industry_TO_Store FOREIGN KEY (IndustryNo) REFERENCES Industry (IndustryNo),
    CONSTRAINT FK_Seller_TO_Store FOREIGN KEY (SellerNo) REFERENCES Seller (SellerNo)
);

-- Item 테이블 생성
CREATE TABLE Item (
    ItemNo INT NOT NULL AUTO_INCREMENT,
    ItemName VARCHAR(50) NULL,
    Amount INT NULL,
    StoreNo INT NOT NULL,
    ItemImage VARCHAR(100) NULL,
    CONSTRAINT PK_Item PRIMARY KEY (ItemNo),
    CONSTRAINT FK_Store_TO_Item FOREIGN KEY (StoreNo) REFERENCES Store (StoreNo)
);

-- Notice 테이블 생성
CREATE TABLE Notice (
    NoticeNo INT NOT NULL AUTO_INCREMENT,
    Title VARCHAR(60) NULL,
    Contents VARCHAR(400) NULL,
    NImage VARCHAR(100)	NULL,
    CreateDate DATE NULL,
    StoreNo INT NOT NULL,
    CONSTRAINT PK_Notice PRIMARY KEY (NoticeNo),
    CONSTRAINT FK_Store_TO_Notice FOREIGN KEY (StoreNo) REFERENCES Store (StoreNo)
);
CREATE TABLE Comments (
    CNO INT NOT NULL AUTO_INCREMENT,
    NContents VARCHAR(100) NULL,
    NoticeNo INT NULL,    
    MemberID VARCHAR(13) NOT NULL,
    CONSTRAINT PK_Comments PRIMARY KEY (CNO),
    CONSTRAINT FK_Notice_TO_Comments FOREIGN KEY (NoticeNo)
        REFERENCES Notice (NoticeNo),
    CONSTRAINT FK_Member_TO_Comments FOREIGN KEY (MemberID)
        REFERENCES Member (MemberID)
);

-- Admin 테이블 생성
CREATE TABLE Admin (
    Admin VARCHAR(13) NOT NULL,
    Pwd VARCHAR(14) NULL,
    AName VARCHAR(26) NULL,
    CONSTRAINT PK_Admin PRIMARY KEY (Admin)
);

-- BlackMember 테이블 생성
CREATE TABLE BlackMember (
    BanNo INT NOT NULL AUTO_INCREMENT,
    MemberID VARCHAR(13) NOT NULL,
    BanDate DATE NULL,
    UnBanDate DATE NULL,
    CONSTRAINT PK_BlackMember PRIMARY KEY (BanNo),
    CONSTRAINT FK_Member_TO_BlackMember FOREIGN KEY (MemberID) REFERENCES Member (MemberID)
);

-- Login 테이블 생성 (LoginTime 타입 변경)
CREATE TABLE Login (
    MemberID VARCHAR(13) NOT NULL,
    LoginCheck INT(1) NULL,
    LoginTime DATETIME NULL,  -- 변경: DATE -> DATETIME
    CONSTRAINT PK_Login PRIMARY KEY (MemberID),
    CONSTRAINT FK_Member_TO_Login FOREIGN KEY (MemberID) REFERENCES Member (MemberID)
);

-- Review 테이블 생성
CREATE TABLE Review (
    ReviewNo INT(10) AUTO_INCREMENT NOT NULL,
    CreateDate DATE NULL,
    ReviewImage VARCHAR(100) NULL,
    Contents VARCHAR(100) NULL,
    Rating INT NULL,
    MemberID VARCHAR(13) NOT NULL,
    StoreNo INT NOT NULL,
    CONSTRAINT PK_Review PRIMARY KEY (ReviewNo),
    CONSTRAINT FK_Member_TO_Review FOREIGN KEY (MemberID) REFERENCES Member (MemberID),
    CONSTRAINT FK_Store_TO_Review FOREIGN KEY (StoreNo) REFERENCES Store (StoreNo)
);
CREATE TABLE ReviewComments (
    CNO INT NOT NULL AUTO_INCREMENT,
    NContents VARCHAR(100) NULL,
    ReviewNo INT NOT NULL,
    MemberID VARCHAR(13) NOT NULL,
    CONSTRAINT PK_ReviewComments PRIMARY KEY (CNO),
    CONSTRAINT FK_Review_TO_ReviewComments 
        FOREIGN KEY (ReviewNo) REFERENCES Review (ReviewNo),
    CONSTRAINT FK_Member_TO_ReviewComments 
        FOREIGN KEY (MemberID) REFERENCES Member (MemberID)
);

CREATE TABLE InterestList (
    MemberID VARCHAR(13) NOT NULL,
    StoreNo INT NOT NULL,
    CONSTRAINT FK_Member_TO_InterestList FOREIGN KEY (MemberID)
        REFERENCES Member (MemberID),
    CONSTRAINT FK_Store_TO_InterestList FOREIGN KEY (StoreNo)
        REFERENCES Store (StoreNo),
    CONSTRAINT PK_InterestList PRIMARY KEY (MemberID, StoreNo)  -- MemberID와 StoreNo 조합을 기본 키로 설정
);

DELIMITER //
CREATE TRIGGER MemberInsert
AFTER INSERT ON Member
FOR EACH ROW
BEGIN
	INSERT INTO Login VALUES(NEW.MemberID,0,now());
END //
DELIMITER ;


-- 데이터 삽입
INSERT INTO Member (MemberID, Pwd, Name, Nick, Birth, Email, MemberImage)
VALUES ('M001', '001', '홍길동', '홍홍', '2000-01-01', 'hong@example.com', 'M001.jpg'),
       ('M002', '002', '김철수', '철철', '1995-05-05', 'kim@example.com', 'M002.jpg'),
       ('M003', '003', '박영희', '영영', '1990-09-09', 'park@example.com', 'M003.jpg'),
       ('M004', '004', '이수민', '수민', '1992-02-14', 'lee@example.com', 'M004.jpg'),
       ('M005', '005', '최재현', '재현', '1987-08-22', 'choi@example.com', 'M005.jpg'),
       ('M006', '006', '정민수', '민수', '1985-03-30', 'jung@example.com', 'M006.jpg'),
       ('M007', '007', '김미영', '미영', '2002-07-15', 'kimmi@example.com', 'M007.jpg'),
       ('M008', '008', '조윤희', '윤희', '1993-10-10', 'jo@example.com', 'M008.jpg'),
       ('M009', '009', '한지우', '지우', '1994-04-04', 'hanjiwoo@example.com', 'M009.jpg'),
       ('M010', '010', '오민정', '민정', '1998-06-18', 'ominjeong@example.com', 'M010.jpg'),
       ('M011', '011', '황석준', '석준', '1986-12-25', 'hwangseokjun@example.com', 'M011.jpg'),
       ('M012', '012', '유미래', '미래', '2001-11-30', 'yumirae@example.com', 'M012.jpg'),
       ('M013', '013', '김영진', '영진', '1999-09-13', 'kimgyeongjin@example.com', 'M013.jpg'),
       ('M014', '014', '박수진', '수진', '1991-01-01', 'parksujin@example.com', 'M014.jpg');

-- 업종 데이터
INSERT INTO Industry (IndustryNo, IndustryName)
VALUES (1, '음식점'),
       (2, '의류'),
       (3, '전자제품'),
       (4, '도서'),
       (5, '홈카페'),
       (6, '운동용품');

-- 판매자 데이터 (기존 3개 + 2개 추가)
INSERT INTO Seller (SellerNo, StorePhone, MemberID)
VALUES (1, '010-1234-5678', 'M001'),
       (2, '010-2345-6789', 'M002'),
       (3, '010-3456-7890', 'M011'),
       (4, '010-4567-8901', 'M007'),
       (5, '010-5678-9012', 'M009'),
       (6, '010-3333-3333', 'M006');

-- 상점 데이터 (주소를 정식 명칭으로 변경)
INSERT INTO Store (StoreName, OpenHours, LOC, StoreImage, SellerNo, IndustryNo)
VALUES 
    ('홍길동 음식점', '09:00-21:00', '서울특별시 강남구 테헤란로 123길 45', 'store1.jpg', 1, 1),
    ('김철수 의류', '10:00-20:00', '서울특별시 마포구 홍대입구로 12길 30', 'store2.jpg', 2, 2),
    ('박영희 전자', '11:00-22:00', '서울특별시 송파구 올림픽로 265', 'store3.jpg', 3, 3),
    ('이수민 도서', '09:00-18:00', '서울특별시 종로구 종로 1가 45', 'store4.jpg', 4, 4),
    ('최재현 홈카페', '08:00-22:00', '서울특별시 강북구 도봉로 243', 'store5.jpg', 5, 5),
    ('햄버거 테스트용 가게', '10:30-22:00', '서울특별시 노원구 상계동 455', 'store6.jpg', 6, 6);


-- 상품 데이터
INSERT INTO Item (ItemName, Amount, StoreNo, ItemImage)
VALUES ('된장찌개', 5000, 1, 'item1.jpg'),
       ('김치찌개', 6000, 1, 'item2.jpg'),
       ('백반' , 7000 , 1, 'item3.jpg'),
       ('고등어조림' , 8000 , 1, 'item4.jpg'),
       ('청바지', 25000, 2, 'item5.jpg'),
       ('면바지', 17000, 2, 'item6.jpg'),
       ('티셔츠', 18000, 2, 'item7.jpg'),
       ('스마트폰', 400000, 3, 'item8.jpg'),
       ('MP3',150000,3,'item9.jpg'),
       ('휴대용게임기',200000,3,'item10.jpg'),
       ('소설책', 15000, 4, 'item11.jpg'),
       ('위인전', 12000, 4, 'item12.jpg'),
       ('전공서', 18000, 4, 'item13.jpg'),
       ('만화책', 9000, 4, 'item14.jpg'),
       ('커피 원두', 25000, 5, 'item15.jpg'),
       ('아메리카노',2000, 5, 'item16.jpg'),
       ('카페라떼',2500, 5,'item17.jpg'),
       ('녹차', 3000, 5,'item18.jpg'),
       ('햄버거1', 5000, 6, 'item19.png'),       
       ('햄버거2',4800, 6,'item20.jpg'),
       ('햄버거3', 6200, 6,'item21.jpg'),
       ('햄버거4', 7300, 6, 'item22.png'),
       ('햄버거5', 2300, 6, 'item23.png'),
       ('햄버거6', 5200, 6, 'item24.png'),
       ('햄버거7', 4700, 6, 'item25.png');
       

-- 리뷰 데이터
INSERT INTO Review (CreateDate, ReviewImage, Contents, Rating, MemberID, StoreNo)
VALUES 
       ('2023-11-01', 'review1.jpg', '맛있어요!', 5, 'M001', 1),
       ('2023-11-03', 'review2.jpg', '좋아요!', 4, 'M007', 2),
       ('2023-11-05', 'review3.jpg', '품질이 좋습니다.', 5, 'M003', 3),
       ('2023-11-08', 'review4.jpg', '책이 재미있어요!', 4, 'M012', 4),
       ('2023-11-10', 'review5.jpg', '커피 원두가 아주 좋네요!', 5, 'M006', 5),
       ('2023-11-12', 'review6.jpg', '맛있고 신선해요!', 4, 'M010', 1),
       ('2023-11-15', 'review7.jpg', '가격대비 괜찮아요.', 3, 'M008', 2),
       ('2023-11-17', 'review8.jpg', '완전 추천해요!', 5, 'M002', 3),
       ('2023-11-20', 'review9.jpg', '친절한 서비스!', 5, 'M014', 4),
       ('2023-11-22', 'review10.jpg', '다시 오고 싶어요!', 4, 'M005', 5),
       ('2023-11-25', 'review11.jpg', '조금 비쌈.', 3, 'M004', 1),
       ('2023-11-27', 'review12.jpg', '너무 멋있어요!', 5, 'M011', 2),
       ('2023-12-01', 'review13.jpg', '재미있고 유익한 책!', 4, 'M013', 3),
       ('2023-12-05', 'review14.jpg', '다양한 메뉴가 있어서 좋아요!', 5, 'M009', 4),
       ('2023-12-08', 'NoImg.jpg', '서비스가 아주 좋습니다.', 5, 'M001', 5),
       ('2023-12-12', 'NoImg.jpg', '또 오고 싶어요.', 4, 'M007', 1),
       ('2023-12-15', 'review17.jpg', '친구들과 와서 좋았어요!', 5,'M003', 2),
       ('2023-12-18', 'review18.jpg', '깔끔하고 정갈해요!', 4, 'M014', 3),
       ('2023-12-22', 'NoImg.jpg', '따뜻하고 맛있어요!', 5, 'M006', 4),
       ('2023-12-25', 'NoImg.jpg', '가성비 좋아요!', 4, 'M012', 5);


-- 공지사항 데이터 (많이 추가)
INSERT INTO Notice (Title, Contents, CreateDate, StoreNo)
VALUES 
    ('신규 공지사항', '새로운 공지사항 내용입니다.', '2024-11-01', 1),
    ('세일 이벤트', '겨울 세일 시작합니다!', '2024-11-05', 2),
    ('점검 안내', '서버 점검이 예정되어 있습니다.', '2024-11-10', 3),
    ('배송 지연 안내', '배송이 지연되고 있습니다. 양해 부탁드립니다.', '2024-11-15', 4),
    ('신제품 출시', '새로운 스마트폰이 출시되었습니다. 많은 관심 부탁드립니다.', '2024-11-20', 5),
    ('주문 마감 안내', '오늘 주문 마감 시간이 23:59입니다. 서둘러 주세요!', '2024-11-25', 1),
    ('2024년 새해 이벤트', '새해 첫 이벤트, 큰 할인 혜택을 놓치지 마세요!', '2024-12-01', 2),
    ('서버 점검 연기', '예정된 서버 점검이 연기되었습니다.', '2024-12-05', 3),
    ('결제 오류 안내', '결제 시스템에 오류가 발생했습니다. 문제 해결 중입니다.', '2024-12-10', 4),
    ('이벤트 당첨자 발표', '지난 이벤트 당첨자를 발표합니다. 확인해 보세요!', '2024-12-15', 5),
    ('배송지 주소 변경 안내', '배송지 주소를 변경하신 분들은 확인해 주세요.', '2024-12-20', 1),
    ('월말 정산 공지', '이번 달 정산에 대한 공지사항입니다.', '2024-12-25', 2),
    ('고객 만족도 조사', '고객님들의 만족도를 조사 중입니다. 많은 참여 부탁드립니다.', '2024-12-30', 3),
    ('운영 정책 변경 안내', '운영 정책이 일부 변경되었습니다. 자세히 확인해 주세요.', '2025-01-02', 4),
    ('품절 안내', '인기 상품이 품절되었습니다. 재입고 예정일을 확인해 주세요.', '2025-01-05', 5),
    ('시스템 업데이트 안내', '시스템 업데이트로 인해 일부 서비스가 일시적으로 중단됩니다.', '2025-01-10', 1),
    ('친환경 패키지 도입', '환경을 고려한 친환경 포장재를 도입했습니다.', '2025-01-15', 2),
    ('회원 전용 할인 혜택', '회원 전용 할인 혜택을 제공합니다. 회원 가입 후 확인하세요!', '2025-01-20', 3);

-- 공지사항 comment
INSERT INTO Comments (NContents, NoticeNo, MemberID)
VALUES 
    ('정말 유용한 정보 감사합니다.', 1, 'M003'),
    ('이 내용은 조금 부족한 것 같아요.', 2, 'M010'),
    ('좋은 게시글이에요. 계속 업데이트 부탁드립니다.', 3, 'M002'),
    ('내용이 잘 정리되어 있어 좋아요.', 4, 'M008'),
    ('유익한 정보네요, 감사해요!', 5, 'M001'),
    ('이 부분이 조금 더 자세했으면 좋겠어요.', 6, 'M014'),
    ('정말 도움이 되었습니다. 감사합니다!', 7, 'M006'),
    ('좀 더 구체적인 예시가 있으면 좋겠어요.', 8, 'M012'),
    ('흥미로운 글이네요. 계속 쓸만한 자료 부탁드려요!', 9, 'M004'),
    ('잘 봤습니다! 계속 좋은 글 부탁드려요.', 5, 'M011'),
    ('좋은 정보 감사합니다. 도움이 되었습니다.', 7, 'M005'),
    ('이 주제에 대해 더 다뤄주세요.', 8, 'M009'),
    ('정리된 글이 잘 읽혔어요. 감사해요!', 9, 'M007'),
    ('훌륭한 글이에요. 자주 올려주세요.', 7, 'M003'),
    ('이 내용도 포함되면 좋을 것 같아요.', 1, 'M002'),
    ('조금 더 깊이 있는 내용이 필요할 것 같아요.', 3, 'M010'),
    ('좋은 포스팅입니다! 더 많이 알려주세요.', 4, 'M013'),
    ('이렇게 쉽게 설명해 주셔서 감사합니다.', 7, 'M006'),
    ('정말 도움이 됐어요. 감사합니다!', 8, 'M012'),
    ('꼭 다시 한번 읽어봐야겠네요. 좋은 정보 감사합니다!', 3, 'M008');

-- 리뷰 comment
INSERT INTO ReviewComments (NContents, ReviewNo, MemberID) 
VALUES 
    ('맛있어 보이네요!', 1, 'M001'),
    ('위치가 어디인가요?', 2, 'M001'),
    ('저도 가보고 싶어요!', 3, 'M001'),
    ('여기 맛있더라고요.', 20, 'M004'),
    ('저도 자주 가는 곳인데 맛있어요.', 19, 'M001'),
    ('사장님이 친절하셔서 좋음!', 20, 'M002'),
    ('맛은 괜찮은데 서비스는 별로...', 4, 'M004'),
    ('저도 가보고 싶어요', 5, 'M003'),
    ('점심시간에 웨이팅이 좀 있어요.', 7, 'M003'),
    ('가끔 좋은 물건 건질 수 있음!', 6, 'M003'),
    ('할인이벤트 할 때 가면 좋아요요', 8, 'M003'),
    ('가격대가 좀 높네요.', 11, 'M003'),
    ('추천 감사합니다!', 13, 'M003'),
    ('분위기가 좋았어요.', 12, 'M003'),
    ('재방문 의사 있습니다.', 17, 'M003'),
    ('점원이 친절했어요.', 19, 'M003'),
    ('인테리어가 예뻐요.', 18, 'M003'),
    ('주차가 불편했어요.', 14, 'M003'),    
    ('친구랑 같이 가면 좋을 것 같아요.', 13, 'M007');

-- 관심가게 목록
INSERT INTO InterestList (MemberID, StoreNo)
VALUES
       ('M001', 1),
       ('M001', 2),
       ('M001', 3),
       ('M001', 4),
       ('M001', 5),
       ('M001', 6),
       ('M007', 5),  -- 김미영 회원이 홈카페에 관심
       ('M012', 3);

COMMIT;

