-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Ноя 07 2022 г., 19:31
-- Версия сервера: 8.0.24
-- Версия PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `shop`
--

-- --------------------------------------------------------

--
-- Структура таблицы `categories`
--

CREATE TABLE `categories` (
  `id` int NOT NULL,
  `name` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Кисти'),
(2, 'Сухие материалы'),
(3, 'акварельные краски'),
(4, 'масляные краски'),
(5, 'основа');

-- --------------------------------------------------------

--
-- Структура таблицы `delivery`
--

CREATE TABLE `delivery` (
  `id` int NOT NULL,
  `id_order` int NOT NULL,
  `client` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `telephone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `delivery`
--

INSERT INTO `delivery` (`id`, `id_order`, `client`, `telephone`, `email`, `address`, `datetime`) VALUES
(1, 2, 'Иванова Галина Павловна', '+123234567890', 'galina99@gmail.com', 'г. Гомель, ул. Советская, д. 56, кв. 4', '2022-11-15 17:18:42'),
(2, 3, 'Глушенок Анна Александровна', '+098765432115', 'anya@gmail.com', 'г. Гомель, ул. Советская, д. 56, кв. 4', '2022-11-06 12:18:42'),
(3, 3, 'Емелин Сергей Андреевич', '+847226502184', 'seriy@gmail.com', 'г. Гомель, ул. Нефтяников, д. 23', '2022-11-23 10:00:00'),
(4, 5, 'Дорофеева Клавдия Семёновна', '+759684751324', 'klavushka@gmail.com', 'г. Мозырь, ул. Фрунзе, д. 34, кв. 87', '2022-12-01 12:18:42'),
(5, 7, 'Гаврилин Пётр Васильевич', '+756947510230', 'petrPerviy@gmail.com', 'г. Брест, ул. Националистов, д. 1, кв. 23', '2022-11-20 11:30:00'),
(6, 6, 'Брель Виктор Валерьевич', '+375648972501', 'brelka@gmail.com', 'г. Гомель, ул. Горького, д. 80', '2022-11-09 08:00:00'),
(7, 10, 'Будько Иван Иванович', '+379558741236', 'byda@gmail.com', 'г. Гомель, ул. Дзержинского, д. 7', '2022-11-18 14:15:00'),
(8, 9, 'Журавлёва Екатерина Авдреевна', '+375441236784', 'kate@gmail.com', 'г. Гомель, ул. Светлогорская, д. 12, кв. 47', '2022-11-10 16:40:00');

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE `orders` (
  `id` int NOT NULL,
  `id_product` int NOT NULL,
  `amount` int NOT NULL,
  `confirmed` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`id`, `id_product`, `amount`, `confirmed`) VALUES
(1, 5, 3, 0),
(2, 1, 2, 1),
(3, 2, 10, 0),
(4, 1, 2, 1),
(5, 2, 10, 0),
(6, 3, 2, 1),
(7, 4, 3, 0),
(8, 5, 5, 1),
(9, 2, 1, 1),
(10, 1, 3, 1),
(11, 8, 2, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `product`
--

CREATE TABLE `product` (
  `id` int NOT NULL,
  `product_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text NOT NULL,
  `price` decimal(65,0) NOT NULL,
  `amount` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `product`
--

INSERT INTO `product` (`id`, `product_code`, `name`, `description`, `price`, `amount`) VALUES
(1, '2347tgi39d771', 'sonet масляная пастель', '36 цветов', '150', 1),
(2, '9i02g38uw87932', 'sonet 8', 'плоская', '1000', 100),
(3, '03356o23g7h23cv23', 'sonet 5', 'овальная', '1000', 120),
(4, '7802g38uw87932', 'sonet 8 щетина', 'плоская', '1000', 100),
(5, '03356o2h23cv23', 'sonet 5 синтетика', 'овальная', '950', 120),
(6, '89uh12f38913f8', 'sonet 15 щетина', 'угловая', '2300', 30),
(7, '190834f9831240', 'sonet 3 синтетика', 'лайнер', '700', 70),
(8, '132f4789217893', 'sonet акварельные краски', '24 цвета', '500', 50),
(9, 'f234872f781871', 'sonet акварельные краски', '16 цветов', '500', 50),
(10, '4f7625h13f847g', 'Белые ночи акварельные краски', '24 цвета', '700', 170),
(11, '6893q2gw542455', 'pinax 7 синтетика', 'угловая', '1250', 50),
(12, 'f31478hv12n813', 'pinax 9 щетина', 'плоская', '1300', 90),
(13, 'c1m84t87nv189x', 'pinax 10 синтетика', 'овальная', '1500', 200),
(14, 'z9o04187vn7234', 'pinax 5 коза', 'лайнер', '900', 350);

-- --------------------------------------------------------

--
-- Структура таблицы `product_category`
--

CREATE TABLE `product_category` (
  `id_category` int NOT NULL,
  `id_product` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `product_category`
--

INSERT INTO `product_category` (`id_category`, `id_product`) VALUES
(1, 2),
(1, 3),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 11),
(1, 12),
(1, 14),
(1, 13),
(3, 8),
(3, 9),
(3, 10);

-- --------------------------------------------------------

--
-- Структура таблицы `ttt`
--

CREATE TABLE `ttt` (
  `id` int NOT NULL,
  `name` varchar(256) NOT NULL,
  `amount` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Дамп данных таблицы `ttt`
--

INSERT INTO `ttt` (`id`, `name`, `amount`) VALUES
(1, '[value-2]', 2),
(2, '[value-2]', 2),
(3, 'name', 5),
(4, 'name', 5),
(5, 'name', 7),
(6, 'name', 7),
(7, 'name', 7),
(8, 'name', 7);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `ttt`
--
ALTER TABLE `ttt`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT для таблицы `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT для таблицы `product`
--
ALTER TABLE `product`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT для таблицы `ttt`
--
ALTER TABLE `ttt`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
