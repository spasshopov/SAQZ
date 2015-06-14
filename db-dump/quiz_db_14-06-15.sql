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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Схема на данните от таблица `quiz`
--

INSERT INTO `quiz` (`id`, `userId`, `password`, `name`, `identifier`, `time`) VALUES
(2, 33, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'myquizname', 'myquizaa', NULL),
(4, 33, 'e7d707a26e7f7b6ff52c489c60e429b1', 'tr', 'trtr', NULL),
(5, 33, 'e10adc3949ba59abbe56e057f20f883e', 'trtrtr', 'trtrtr', NULL),
(6, 33, 'e10adc3949ba59abbe56e057f20f883e', 'my quiz', 'myquiz', 10),
(7, 33, 'e10adc3949ba59abbe56e057f20f883e', 'super_quiz', 'superquiz', NULL),
(8, 33, 'e10adc3949ba59abbe56e057f20f883e', 'of', 'of', NULL),
(9, 33, 'e10adc3949ba59abbe56e057f20f883e', 'myquiz', '9op', NULL),
(10, 33, 'e10adc3949ba59abbe56e057f20f883e', 'retro quiz', 'retro', 5),
(11, 33, 'e10adc3949ba59abbe56e057f20f883e', 'super man quiz', 'superman', 4),
(12, 33, 'e10adc3949ba59abbe56e057f20f883e', 'pop', 'superman2', 8);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
