-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Време на генериране: 
-- Версия на сървъра: 5.5.32
-- Версия на PHP: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- БД: `quiz_db`
--
CREATE DATABASE IF NOT EXISTS `quiz_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `quiz_db`;

-- --------------------------------------------------------

--
-- Структура на таблица `answers`
--

CREATE TABLE IF NOT EXISTS `answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `correct` tinyint(1) NOT NULL,
  `answer` text NOT NULL,
  `index` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=192 ;

--
-- Схема на данните от таблица `answers`
--

INSERT INTO `answers` (`id`, `question_id`, `correct`, `answer`, `index`) VALUES
(34, 10, 1, 'Bring presents.', 0),
(35, 10, 0, 'Steal cookies.', 1),
(36, 10, 0, 'Sleep on christmass.', 2),
(37, 10, 0, 'Run maratons.', 3),
(38, 11, 0, 'In Australia', 0),
(39, 11, 1, 'In Europe.', 1),
(40, 11, 0, 'In America', 2),
(41, 11, 0, 'In Asia', 3),
(42, 12, 0, 'H2O3', 0),
(43, 12, 0, 'H02', 1),
(44, 12, 1, 'H2O', 2),
(45, 12, 0, 'C2O', 3),
(46, 13, 1, '681', 0),
(47, 13, 0, '788', 1),
(48, 13, 0, '10298', 2),
(49, 13, 0, '689', 3),
(50, 14, 0, 'Brasil', 0),
(51, 14, 0, 'Italy', 1),
(52, 14, 1, 'Germany', 2),
(53, 14, 0, 'Bulgaria', 3),
(64, 19, 1, 'indeed', 0),
(65, 19, 0, 'yes', 1),
(66, 19, 0, 'no', 2),
(67, 19, 0, 'kill', 3),
(68, 20, 0, 'yes', 0),
(69, 20, 0, 'yes yes', 1),
(70, 20, 0, 'yes ', 2),
(71, 20, 1, 'yes', 3),
(72, 21, 1, 'yes', 0),
(73, 21, 0, 'no', 1),
(74, 21, 0, 'I am dog', 2),
(75, 21, 0, 'I am cat', 3),
(80, 23, 1, 'They stare a lot.', 0),
(81, 23, 0, 'They have too much action', 1),
(82, 23, 0, 'They invest a lot of money', 2),
(83, 23, 0, 'They use new technologies', 3),
(84, 24, 0, 'The president of USA', 0),
(85, 24, 1, 'The leader of the autobots', 1),
(86, 24, 0, 'Batman', 2),
(87, 24, 0, 'Agent 007', 3),
(92, 26, 1, 'a', 0),
(93, 26, 0, 'b', 1),
(94, 26, 0, 'c', 2),
(95, 26, 0, 'd', 3),
(96, 27, 0, 'Barry White', 0),
(97, 27, 0, 'Bary Adam', 1),
(98, 27, 1, 'Clarck Kent', 2),
(99, 27, 0, 'David Guetta', 3),
(100, 28, 0, 'Mars', 0),
(101, 28, 0, 'Saturn', 1),
(102, 28, 0, 'The Moon', 2),
(103, 28, 1, 'Crypton', 3),
(104, 29, 1, 'ability to fly', 0),
(105, 29, 0, 'ability to sing like Michel Jackson', 1),
(106, 29, 0, 'The biggest head in the world', 2),
(107, 29, 0, 'Girlfriend called Joshomakanata', 3),
(108, 30, 0, 'John Travolta', 0),
(109, 30, 1, 'Lex Lutter', 1),
(110, 30, 0, 'The Flash', 2),
(111, 30, 0, 'Doctor Who', 3),
(112, 31, 0, 'Sugar', 0),
(113, 31, 0, 'Gold', 1),
(114, 31, 1, 'Cryptonite', 2),
(115, 31, 0, 'Dynamite', 3),
(116, 32, 1, 'Batman', 0),
(117, 32, 0, 'Lex Lutter', 1),
(118, 32, 0, 'Cindy Crowdford', 2),
(119, 32, 0, 'Aquaman', 3),
(120, 33, 1, 'mis Lane', 0),
(121, 33, 0, 'Wonder Woman', 1),
(122, 33, 0, 'The Flash', 2),
(123, 33, 0, 'Lex Lutter', 3),
(128, 35, 1, 'Creature from outter space.', 0),
(129, 35, 0, 'A dog', 1),
(130, 35, 0, 'A cat', 2),
(131, 35, 0, 'A fish', 3),
(132, 36, 1, 'Yes if they are from Mars', 0),
(133, 36, 0, 'Yes if they are from the Moon', 1),
(134, 36, 0, 'No', 2),
(135, 36, 0, 'Yes if they live on SAturn', 3),
(136, 37, 1, 'tv', 0),
(137, 37, 0, 'car', 1),
(138, 37, 0, 'cat', 2),
(139, 37, 0, 'dog', 3),
(140, 38, 0, 'yes', 0),
(141, 38, 0, 'no', 1),
(142, 38, 1, 'fuck', 2),
(143, 38, 0, 'how', 3),
(148, 40, 0, '1', 0),
(149, 40, 1, '2', 1),
(150, 40, 0, '3', 2),
(151, 40, 0, '4', 3),
(152, 41, 0, '1', 0),
(153, 41, 0, '2', 1),
(154, 41, 1, '3', 2),
(155, 41, 0, '4', 3),
(156, 42, 0, '1', 0),
(157, 42, 0, '2', 1),
(158, 42, 0, '3', 2),
(159, 42, 1, '4', 3),
(160, 43, 0, 'sdf', 0),
(161, 43, 0, 'sdfs', 1),
(162, 43, 1, 'df', 2),
(163, 43, 0, 'sdfsddsfds', 3),
(164, 44, 1, 'Mamels', 0),
(165, 44, 0, 'Fishes', 1),
(166, 44, 0, 'Aliens', 2),
(167, 44, 0, 'Dragons', 3),
(168, 45, 0, 'Africa', 0),
(169, 45, 0, 'Australia', 1),
(170, 45, 1, 'Sybir', 2),
(171, 45, 0, 'UK', 3),
(172, 46, 1, 'Bark', 0),
(173, 46, 0, 'Meow', 1),
(174, 46, 0, 'Fly', 2),
(175, 46, 0, 'Live with cats', 3),
(176, 47, 0, 'just a test', 0),
(177, 47, 0, 'just a test', 1),
(178, 47, 0, 'just a test', 2),
(179, 47, 1, 'ehe', 3),
(180, 48, 0, 'cars', 0),
(181, 48, 1, 'ball', 1),
(182, 48, 0, 'dog', 2),
(183, 48, 0, 'bat', 3),
(184, 49, 0, 'Germany', 0),
(185, 49, 0, 'France', 1),
(186, 49, 1, 'Brazil', 2),
(187, 49, 0, 'Bulgaria', 3),
(188, 50, 1, 'Italy', 0),
(189, 50, 0, 'Spain', 1),
(190, 50, 0, 'Germany', 2),
(191, 50, 0, 'Afganistan', 3);

-- --------------------------------------------------------

--
-- Структура на таблица `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `reports` int(11) DEFAULT '0',
  `quiz_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=51 ;

--
-- Схема на данните от таблица `questions`
--

INSERT INTO `questions` (`id`, `question`, `user_id`, `reports`, `quiz_id`) VALUES
(10, 'What Santa do?', 27, 4, 6),
(11, 'Where is Bulgaria?', 27, 4, 6),
(12, 'Which one is water?', 27, 4, 6),
(13, 'When is Bulgaria founded?', 27, 4, 6),
(14, 'Who won the world cup in 2014?', 27, 4, 6),
(19, 'Good questions?', 33, 4, 6),
(20, 'Another one bites the dust?', 33, 4, 6),
(21, 'Is the Flash cool?', 33, 4, 7),
(22, 'What is WOW?', 33, 4, 7),
(23, 'Why idian movies are boring?', 33, 4, 7),
(24, 'Who is Optimus Prime?', 33, 4, 7),
(26, 'This is amazing', 33, 4, 9),
(27, 'Who is Superman?', 33, 4, 11),
(28, 'Superman is from?', 33, 4, 11),
(29, 'Superman has...', 33, 4, 11),
(30, 'Supermans biggest enemy is..', 33, 4, 11),
(31, 'Superman can be hurt by..', 33, 4, 11),
(32, 'Superman best friend is?', 33, 4, 11),
(33, 'Superman have a crush for...', 33, 4, 11),
(35, 'What is an alien?', 33, 4, 13),
(36, 'Are alien green?', 33, 4, 13),
(37, 'Btv is', 33, 4, 14),
(38, 'Btv sucks?', 33, 4, 14),
(40, 'question 2', 34, 4, 15),
(41, 'Question 3', 34, 4, 15),
(42, 'Question 4', 34, 4, 15),
(43, 'sdf', 33, 4, NULL),
(44, 'Dogs are?', 34, 4, 16),
(45, 'The Husky lives in?', 34, 4, 16),
(46, 'Dogs love to?', 34, 4, 16),
(47, 'question for alabala quiz?', 34, 4, 0),
(48, 'It is played with?', 34, 0, 17),
(49, 'Who won most world cups?', 34, 0, 17),
(50, 'Juventus us football team from wich country?', 34, 0, 17);

-- --------------------------------------------------------

--
-- Структура на таблица `quiz`
--

CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

--
-- Схема на данните от таблица `quiz`
--

INSERT INTO `quiz` (`id`, `userId`, `password`, `name`, `identifier`, `time`) VALUES
(2, 33, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'myquizname', 'myquizaa', NULL),
(4, 33, 'e7d707a26e7f7b6ff52c489c60e429b1', 'tr', 'trtr', NULL),
(6, 33, 'e10adc3949ba59abbe56e057f20f883e', 'my quiz', 'myquiz', 10),
(7, 33, 'e10adc3949ba59abbe56e057f20f883e', 'super_quiz', 'superquiz', NULL),
(10, 33, 'e10adc3949ba59abbe56e057f20f883e', 'retro quiz', 'retro', 5),
(11, 34, 'e10adc3949ba59abbe56e057f20f883e', 'super man quiz', 'superman', 4),
(12, 33, 'e10adc3949ba59abbe56e057f20f883e', 'pop', 'superman2', 8),
(13, 33, 'e10adc3949ba59abbe56e057f20f883e', 'alien quiz', 'alien', 2),
(14, 33, 'e10adc3949ba59abbe56e057f20f883e', 'btv', 'btv', 3),
(15, 34, 'e10adc3949ba59abbe56e057f20f883e', 'Super interesting quiz', 'superquiz1', 6),
(16, 34, 'e10adc3949ba59abbe56e057f20f883e', 'Dog quiz', 'dog', 9),
(17, 34, 'e10adc3949ba59abbe56e057f20f883e', 'football quiz', 'football', 10);

-- --------------------------------------------------------

--
-- Структура на таблица `quiz_history`
--

CREATE TABLE IF NOT EXISTS `quiz_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `success_rate` decimal(5,0) NOT NULL,
  `wrong_answered` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=142 ;

--
-- Схема на данните от таблица `quiz_history`
--

INSERT INTO `quiz_history` (`id`, `user_id`, `quiz_id`, `success_rate`, `wrong_answered`) VALUES
(126, 33, 6, '28', 'What Santa do?\nWhere is Bulgaria?\nWhich one is water?\nWhen is Bulgaria founded?\nAnother one bites the dust?\n'),
(127, 33, 6, '42', 'When is Bulgaria founded?\nWho won the world cup in 2014?\nGood questions?\nAnother one bites the dust?\n'),
(128, 33, 7, '100', ''),
(129, 33, 6, '85', 'Good questions?\n'),
(130, 33, 6, '28', 'Where is Bulgaria?\nWhen is Bulgaria founded?\nWho won the world cup in 2014?\nGood questions?\nAnother one bites the dust?\n'),
(131, 33, 11, '85', 'Supermans biggest enemy is..\n'),
(132, 33, 14, '100', ''),
(133, 33, 14, '50', 'Btv is\n'),
(134, 33, 14, '50', ''),
(135, 33, 14, '50', ''),
(136, 34, 15, '100', ''),
(137, 33, 15, '50', 'question 2\nQuestion 4\n'),
(138, 33, 11, '57', 'Superman is from?\nSupermans biggest enemy is..\nSuperman have a crush for...\n'),
(139, 34, 11, '14', 'Who is Superman?\nSuperman is from?\nSuperman has...\nSupermans biggest enemy is..\nSuperman can be hurt by..\nSuperman have a crush for...\n'),
(140, 34, 11, '28', 'Superman has...\nSupermans biggest enemy is..\nSuperman can be hurt by..\nSuperman best friend is?\nSuperman have a crush for...\n'),
(141, 34, 17, '50', 'question for alabala quiz?\nJuventus us football team from wich country?\n');

-- --------------------------------------------------------

--
-- Структура на таблица `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `points` int(11) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `number_answered` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=36 ;

--
-- Схема на данните от таблица `user`
--

INSERT INTO `user` (`id`, `username`, `nickname`, `points`, `password`, `number_answered`) VALUES
(27, 'testuser@gmail.com', 'john', 94, 'e10adc3949ba59abbe56e057f20f883e', 99),
(28, 'bambuk@gmail.com', 'Bartador', 101, '7b71d9fdfe5b15a2d1a4968c195f93ae', 106),
(29, 'gogogog@gmail.com', 'Gorgoroth', 97, 'sdfdsf', 100),
(30, 'robokob@gmail.com', 'robokob', 41, 'e10adc3949ba59abbe56e057f20f883e', 100),
(31, 'someemail@gmail.com', 'sam', 0, 'e10adc3949ba59abbe56e057f20f883e', 0),
(32, 'someeamil@gmail.com', 'some', 0, 'e10adc3949ba59abbe56e057f20f883e', 0),
(33, 'kredit@gmail.com', 'Tommy', 99, 'e10adc3949ba59abbe56e057f20f883e', 105),
(34, 's.v.shopov@gmail.com', 'Mr. Smith', 98, 'e10adc3949ba59abbe56e057f20f883e', 106),
(35, 'johnsnow@gmail.com', 'John Snow', 1, 'e10adc3949ba59abbe56e057f20f883e', 99);

-- --------------------------------------------------------

--
-- Структура на таблица `user_answered`
--

CREATE TABLE IF NOT EXISTS `user_answered` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=72 ;

--
-- Схема на данните от таблица `user_answered`
--

INSERT INTO `user_answered` (`id`, `user_id`, `question_id`) VALUES
(69, 34, 10),
(70, 34, 11),
(71, 34, 12);

--
-- Ограничения за дъмпнати таблици
--

--
-- Ограничения за таблица `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`);

--
-- Ограничения за таблица `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения за таблица `user_answered`
--
ALTER TABLE `user_answered`
  ADD CONSTRAINT `user_answered_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  ADD CONSTRAINT `user_answered_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
