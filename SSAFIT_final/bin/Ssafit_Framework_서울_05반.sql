CREATE database if not exists `ssafit`;

use `ssafit`;

CREATE TABLE if not exists `user` (
  `user_id` varchar(45) NOT NULL,
  `user_nickname` varchar(45) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_email` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists `video` (
  `video_id` int NOT NULL,
  `video_title` varchar(45) NOT NULL,
  `part` varchar(45) NOT NULL,
  `channelname` varchar(45) NOT NULL,
  `url` varchar(45) NOT NULL,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE if not exists `review` (
  `review_title` varchar(45) DEFAULT NULL,
  `review_content` varchar(45) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `view_cnt` int DEFAULT 0,
  `video_id` int NOT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`create_time`,`video_id`,`user_id`),
  KEY `fk_Review_Video_idx` (`video_id`),
  KEY `fk_Review_User_idx` (`user_id`),
  CONSTRAINT `fk_Review_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_Review_Video` FOREIGN KEY (`video_id`) REFERENCES `video` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE if not exists `follow` (
  `video_id` int NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`video_id`,`user_id`),
  KEY `fk_Follow_Video1_idx` (`video_id`),
  KEY `fk_Follow_User1_idx` (`user_id`),
  CONSTRAINT `fk_Follow_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_Follow_Video1` FOREIGN KEY (`video_id`) REFERENCES `video` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- USER 테이블 더미 데이터
INSERT INTO `user` (`user_id`, `user_nickname`, `user_password`, `user_email`) VALUES
('jhsong', '송지훈', 'pass1234', 'jhsong@example.com'),
('hycho', '조한영', 'secure456', 'hycho@example.com'),
('minpark', '박민수', 'password789', 'minpark@example.com'),
('youngkim', '김영진', 'myPass101', 'youngkim@example.com'),
('jiyoon', '윤지영', 'strongPass', 'jiyoon@example.com'),
('sarahlee', '이사라', 'leeSarah789', 'sarahlee@example.com'),
('dongwook', '동욱', 'dwook@pass', 'dongwook@example.com'),
('hjkim', '김현주', 'hjkim987', 'hjkim@example.com'),
('sujiwoo', '우수지', 'wooSuji001', 'sujiwoo@example.com'),
('eunhye', '이은혜', 'eunhye123', 'eunhye@example.com');

-- VIDEO 테이블 더미 데이터
INSERT INTO `video` (`video_id`, `video_title`, `part`, `channelname`, `url`) VALUES
(1, '전신 스트레칭 루틴', '전신', 'HealthyLife', 'http://example.com/stretch'),
(2, '초보자용 상체 운동', '상체', 'FitForLife', 'http://example.com/upperbody'),
(3, '홈트 하체운동', '하체', 'HomeWorkout', 'http://example.com/lowerbody'),
(4, '유산소 운동 20분', '유산소', 'CardioKing', 'http://example.com/cardio'),
(5, '복근 운동 루틴', '복부', 'AbsMaster', 'http://example.com/abs'),
(6, '스트레칭 가이드', '전신', 'StretchZone', 'http://example.com/stretch-guide'),
(7, '근력 운동 팁', '상체', 'PowerFitness', 'http://example.com/strength'),
(8, '전신 운동 프로그램', '전신', 'FullBodyWorks', 'http://example.com/fullbody'),
(9, '탄탄한 하체 만들기', '하체', 'LegsDay', 'http://example.com/legs'),
(10, '유산소 초보자 가이드', '유산소', 'BeginnerFit', 'http://example.com/beginner-cardio');

-- REVIEW 테이블 더미 데이터 (다양한 create_time)
INSERT INTO `review` (`review_title`, `review_content`, `create_time`, `view_cnt`, `video_id`, `user_id`) VALUES
('좋은 스트레칭 영상입니다.', '몸이 정말 가벼워졌어요!', '2024-10-28 08:30:00', 30, 1, 'jhsong'),
('상체 운동 처음 해봤어요.', '초보자에게 딱입니다!', '2024-10-29 12:45:00', 45, 2, 'hycho'),
('하체 힘들었지만 보람 있었어요', '영상 퀄리티도 좋고 따라하기 쉬워요.', '2024-10-30 15:15:00', 20, 3, 'minpark'),
('20분으로 땀 빼는 유산소', '초보자도 쉽게 따라할 수 있어요.', '2024-10-31 10:00:00', 60, 4, 'youngkim'),
('복근에 힘이 들어갑니다!', '꾸준히 하면 효과 볼 것 같아요.', '2024-11-01 17:30:00', 55, 5, 'jiyoon'),
('유익한 스트레칭 가이드', '스트레칭 하는 법 잘 배웠어요.', '2024-11-02 09:20:00', 10, 6, 'sarahlee'),
('상체 운동 프로그램 최고입니다', '강력 추천합니다!', '2024-11-03 18:45:00', 70, 7, 'dongwook'),
('전신 운동 너무 좋아요', '매일 할 계획입니다.', '2024-11-04 14:50:00', 85, 8, 'hjkim'),
('하체 운동 효과 있네요', '짧은 시간에 높은 효율!', '2024-11-05 07:10:00', 40, 9, 'sujiwoo'),
('유산소 따라하기 편해요', '운동 초보에게 추천합니다.', '2024-11-06 13:05:00', 90, 10, 'eunhye');

-- FOLLOW 테이블 더미 데이터 (다양한 create_time)
INSERT INTO `follow` (`video_id`, `user_id`, `create_time`) VALUES
(1, 'hycho', '2024-10-28 11:30:00'),
(2, 'minpark', '2024-10-29 14:00:00'),
(3, 'youngkim', '2024-10-30 19:25:00'),
(4, 'jiyoon', '2024-10-31 08:10:00'),
(5, 'sarahlee', '2024-11-01 13:45:00'),
(6, 'dongwook', '2024-11-02 16:20:00'),
(7, 'hjkim', '2024-11-03 20:35:00'),
(8, 'sujiwoo', '2024-11-04 12:55:00'),
(9, 'eunhye', '2024-11-05 06:45:00'),
(10, 'jhsong', '2024-11-06 10:15:00');

