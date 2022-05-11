-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 05 Jun 2021 pada 12.42
-- Versi server: 10.4.17-MariaDB
-- Versi PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fitguru`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `calories`
--

CREATE TABLE `calories` (
  `id` int(11) NOT NULL,
  `calories` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `calories`
--

INSERT INTO `calories` (`id`, `calories`) VALUES
(1, '800');

-- --------------------------------------------------------

--
-- Struktur dari tabel `login_information`
--

CREATE TABLE `login_information` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `login_information`
--

INSERT INTO `login_information` (`id`, `username`) VALUES
(1, 'andrebillys');

-- --------------------------------------------------------

--
-- Struktur dari tabel `product`
--

CREATE TABLE `product` (
  `product_id` varchar(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_category` varchar(255) NOT NULL,
  `product_description` varchar(255) NOT NULL,
  `product_picture` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_category`, `product_description`, `product_picture`) VALUES
('ACS01', 'OVICX Yoga Mat', 'Accessories', 'Material : NBR (Nitrile rubber) atau karet nitril merupakan karet sintetis yang tahan terhadap minyak. Matras untuk Yoga, senam, ataupun pilates yang bisa juga digunakan untuk latihan perut.', 'http://prak11billy2-com.preview-domain.com/pictures/ACS01.jpg'),
('ACS02', 'PowerBelt Gym Fitness', 'Accessories', 'Menambah Power dan Percaya Diri Saat Lifting. Mencegah Cedera Punggung & Pinggang. Menjaga pernafasan perut', 'http://prak11billy2-com.preview-domain.com/pictures/ACS02.jpg'),
('ACS03', 'SFIDN FITS Premium Long Resistance Band', 'Accessories', 'Terbuat dari 100% lateks alami dan memiliki elastisitas yang baik sehingga sangat ramah lingkungan. Ini lebih tahan aus dan kokoh daripada resistan umum. ', 'http://prak11billy2-com.preview-domain.com/pictures/ACS03.jpg'),
('DMB01', 'Dumbbell Barbell Set 20 kg', 'Dumbbell', 'Dumble yang terbuat dari bahan besi cor. Dibuat dengan menggunakan teknologi tinggi, membuat dumbbell set SPEEDS sangatlah kuat dan aman ketika digunakan dengan pengangan yang disesuaikan dengan bentuk tangan menusia membuat dumbbell ini sangat nyaman dig', 'http://prak11billy2-com.preview-domain.com/pictures/DMB01.jpg'),
('DMB02', 'OneTwoFit Dumbbell Barbell Set 15 kg', 'Dumbbell', 'Dapat dilepas: Dilepas tujuan ganda, halter plus barbel variabel batang penghubung. Berat yang dapat disesuaikan: Halter dapat disesuaikan dengan bebas, menambah atau mengurangi sesuai dengan kebutuhan mereka sendiri, untuk memenuhi intensitas pelatihan p', 'http://prak11billy2-com.preview-domain.com/pictures/DMB02.jpg'),
('DMB03', 'Bowflex adjustable Dumbbell 24 kg', 'Dumbbell', 'Menggantikan 15 barbel menjadi 1 buah barbel dan Praktis digunakan. 1 Dumbell bisa diubah menjadi berat 2kg,3kg,4kg,5kg,7kg,8kg,9kg,10kg,12kg,14kg,16kg,18kg,20kg,23kg,24kg', 'http://prak11billy2-com.preview-domain.com/pictures/DMB03.jpg'),
('SPP01', 'NITROTECH 4 LBS MUSCLETECH NITRO TECH 4LBS WHEY PROTEIN ISOLATE', 'Supplement', 'Rendah kalori (160 Sekali Serving). Tinggi protein (30 gr Sekali Serving). Tinggi BCAA (5,5gr). Ada Glutamine dan Creatine.', 'http://prak11billy2-com.preview-domain.com/pictures/SPP01.png'),
('SPP02', 'BCAA Ultimate Nutrition 500MG', 'Supplement', 'BCAA Ultimate juga merupakan satu jenis suplemen yang cukup penting untuk anda yang sungguh-sungguh ingin memiliki bentuk tubuh menawan dengan melakukan latihan fitness.', 'http://prak11billy2-com.preview-domain.com/pictures/SPP02.jpg'),
('SPP03', 'Evolene Crevolene Creatine 330', 'Supplement', 'Evolene Creatine adalah salah satu suplemen inti yang wajib untuk dikonsumsi secara rutin, terutama jika anda dalam masa pembentukan otot.\r\nKandungan creatine alami yang terdapat di dalam suplemen Evolene bisa memberikan cukup banyak manfaat bagi pertumbu', 'http://prak11billy2-com.preview-domain.com/pictures/SPP03.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `rating`
--

CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `rate` varchar(5) NOT NULL,
  `comment` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `rating`
--

INSERT INTO `rating` (`id`, `rate`, `comment`) VALUES
(1, '3', 'Good App!');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fullname` text NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(300) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `address` varchar(255) NOT NULL,
  `weight` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `fullname`, `username`, `password`, `email`, `phone`, `address`, `weight`) VALUES
(1, 'Andre Billy', 'andrebillys', 'andre123', 'andrebilly55@gmail.com', '087883494959', 'Victoria Park', '75'),
(3, 'nicholas', 'nicholas', 'nicholas', 'nicholas@gmail.com', '087883494952', 'Serpong Raya', '70');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `calories`
--
ALTER TABLE `calories`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `login_information`
--
ALTER TABLE `login_information`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD UNIQUE KEY `product_name` (`product_name`);

--
-- Indeks untuk tabel `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `calories`
--
ALTER TABLE `calories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `login_information`
--
ALTER TABLE `login_information`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `rating`
--
ALTER TABLE `rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
