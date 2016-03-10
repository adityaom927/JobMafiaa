-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 31, 2015 at 06:02 AM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `job`
--

-- --------------------------------------------------------

--
-- Table structure for table `ads`
--

CREATE TABLE IF NOT EXISTS `ads` (
  `ad_id` int(11) NOT NULL,
  `ad_head` varchar(1000) NOT NULL,
  `ad_content` text NOT NULL,
  `ad_contact` varchar(1000) NOT NULL,
  `ad_image` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ads`
--

INSERT INTO `ads` (`ad_id`, `ad_head`, `ad_content`, `ad_contact`, `ad_image`) VALUES
(1, 'new car on diwali ', 'Festival Discount Schemes & Offers on Cars for November 2015. ... » November 2015 - Dhanteras, Diwali Festival Discounts Schemes to Roll on Cars. ... ', '985624521', ''),
(3, '', '', '', 'Z2NV46v.jpg'),
(4, '', '', '', 'fedex-advertisement.jpg'),
(5, '', '', '', 'nike_dunk_sb_advertisement_by_leroup.png'),
(6, 'sale sale sale ...', 'get 50% off on date 10-11-2015 at vishal.', '78542133', ''),
(7, 'mobile services', 'with very few amount ', 'mobishop@gmail.com', '');

-- --------------------------------------------------------

--
-- Table structure for table `certificate`
--

CREATE TABLE IF NOT EXISTS `certificate` (
  `cer_id` int(11) NOT NULL,
  `user_email` varchar(500) NOT NULL,
  `name` varchar(300) NOT NULL,
  `file` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE IF NOT EXISTS `companies` (
  `com_id` int(11) NOT NULL,
  `com_name` varchar(200) NOT NULL,
  `logo` varchar(500) NOT NULL,
  `start_year` date NOT NULL,
  `com_address` varchar(500) NOT NULL,
  `com_contact` varchar(50) NOT NULL,
  `com_email` varchar(200) NOT NULL,
  `type` varchar(100) NOT NULL,
  `com_link` varchar(500) NOT NULL,
  `zip` varchar(200) NOT NULL,
  `city` varchar(200) NOT NULL,
  `state` varchar(200) NOT NULL,
  `country` varchar(200) NOT NULL,
  `contact_person` varchar(200) NOT NULL,
  `about_company` text NOT NULL,
  `industry` varchar(300) NOT NULL,
  `pay_status` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`com_id`, `com_name`, `logo`, `start_year`, `com_address`, `com_contact`, `com_email`, `type`, `com_link`, `zip`, `city`, `state`, `country`, `contact_person`, `about_company`, `industry`, `pay_status`) VALUES
(2, 'Avalanche Infotech Pvt Ltd', '7.jpg', '2015-11-12', '221-223 manas bhawan indore ,mp', '8987563222', 'avl@gmail.com', 'company', 'http://avalancheinfotech.com', '452015', 'Indore', 'Madhya Pradesh', 'India', 'abhishek', '', 'Information technology', ''),
(3, 'Vishal Consultant', 'logo.png', '2000-10-01', 'b21 geeta bhavan square , indore', '2147483647', 'vishal@gmail.com', 'consultancy', 'http://abc.com', '452015', 'indore', 'Madhya Pradesh', 'India', 'sahil singh', 'Present so many websites  given login with facebook .no need to create database tables and store the data into tables unnecessary data and waste of time.  Here i will explain how to login with facbook in codeignator, core php also same procedure', 'Information technology', ''),
(6, 'Abhishek''s Solutions', 'wheels.png', '2009-12-01', '', '7845126354', 'company@gmail.com', 'company', '', '452015', 'Indore', 'Madhya Pradesh', 'India', 'abhishek', 'hello this is my company', 'Information technology', '');

-- --------------------------------------------------------

--
-- Table structure for table `counter`
--

CREATE TABLE IF NOT EXISTS `counter` (
  `id` int(11) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `counter`
--

INSERT INTO `counter` (`id`, `total`) VALUES
(0, 1434);

-- --------------------------------------------------------

--
-- Table structure for table `education`
--

CREATE TABLE IF NOT EXISTS `education` (
  `edu_id` int(11) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `class` varchar(100) NOT NULL,
  `percentage` int(11) NOT NULL,
  `board` varchar(200) NOT NULL,
  `passing_year` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `education`
--

INSERT INTO `education` (`edu_id`, `user_email`, `class`, `percentage`, `board`, `passing_year`) VALUES
(2, 'abhi@gmail.com', 'Higher Secondry', 71, 'Mp Board', '2010'),
(3, 'abhi@gmail.com', 'bca', 69, 'davv', '2013'),
(4, 'abhi@gmail.com', 'High School', 61, 'Mp Board', '2008');

-- --------------------------------------------------------

--
-- Table structure for table `experience`
--

CREATE TABLE IF NOT EXISTS `experience` (
  `exp_id` int(11) NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `post` varchar(100) NOT NULL,
  `industry` varchar(500) NOT NULL,
  `exp_year` varchar(100) NOT NULL,
  `exp_month` varchar(100) NOT NULL,
  `exp_from_month` varchar(100) NOT NULL,
  `exp_from_year` varchar(100) NOT NULL,
  `exp_to_month` varchar(100) NOT NULL,
  `exp_to_year` varchar(100) NOT NULL,
  `current_work` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `experience`
--

INSERT INTO `experience` (`exp_id`, `user_email`, `company_name`, `post`, `industry`, `exp_year`, `exp_month`, `exp_from_month`, `exp_from_year`, `exp_to_month`, `exp_to_year`, `current_work`) VALUES
(1, 'abhi@gmail.com', 'avalanche infotech', 'php developer', 'Computer Applicaition', '0 yr', '7 months', 'May', '2015', 'December', '2015', 'no'),
(2, 'abhi@gmail.com', 'mobi web tecnologies pvt. ltd             ', 'php devloper', 'Computer Applicaition', '1 yr', '2 months', 'January', '2014', '', '', 'yes'),
(3, 'abhi@gmail.com', 'mobi web tecnologies pvt. ltd    ', 'php developer', 'Computer Applicaition', '1 yr', '2 months', 'January', '2014', 'January', '2014', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `favourite`
--

CREATE TABLE IF NOT EXISTS `favourite` (
  `fav_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `user_email` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favourite`
--

INSERT INTO `favourite` (`fav_id`, `job_id`, `user_email`) VALUES
(3, 4, 'abhi@gmail.com'),
(4, 5, 'abhi@gmail.com'),
(5, 1, 'abhi@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `fields`
--

CREATE TABLE IF NOT EXISTS `fields` (
  `field_id` int(11) NOT NULL,
  `field_name` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fields`
--

INSERT INTO `fields` (`field_id`, `field_name`) VALUES
(1, 'Information technology'),
(2, 'Computer Applicaition'),
(3, 'web developer'),
(4, 'programmer'),
(5, 'data entry'),
(6, 'web designer');

-- --------------------------------------------------------

--
-- Table structure for table `job_posted`
--

CREATE TABLE IF NOT EXISTS `job_posted` (
  `job_id` int(11) NOT NULL,
  `com_email` varchar(100) NOT NULL,
  `job_title` varchar(100) NOT NULL,
  `skills_required` varchar(500) NOT NULL,
  `experience` varchar(100) NOT NULL,
  `job_description` text NOT NULL,
  `job_location` varchar(100) NOT NULL,
  `last_date` date NOT NULL,
  `min_qualification` varchar(100) NOT NULL,
  `vacancy` int(11) NOT NULL,
  `category` varchar(200) NOT NULL,
  `job_type` varchar(200) NOT NULL,
  `salary_to` varchar(200) NOT NULL,
  `salary_from` varchar(200) NOT NULL,
  `posted_date` date NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `job_posted`
--

INSERT INTO `job_posted` (`job_id`, `com_email`, `job_title`, `skills_required`, `experience`, `job_description`, `job_location`, `last_date`, `min_qualification`, `vacancy`, `category`, `job_type`, `salary_to`, `salary_from`, `posted_date`) VALUES
(1, 'avl@gmail.com', 'programmer', 'c, c++, php', '1 year', 'strong programming skills', 'indore', '2015-10-08', 'graduation', 23, 'programmer', 'company', '300000', '100000', '2015-12-01'),
(2, 'avl@gmail.com', 'web developer', 'php , codeigniter', '1 year', 'candidate must have very strong knowledge of oop', 'bhopal', '2015-11-12', 'graduation', 10, 'web developer', 'company', '500000', '100000', '2015-08-12'),
(3, 'avl@gmail.com', 'data entry', 'typing speed: 25wpm', 'fresher', 'must able to read and understand english language.', 'indore', '2015-11-15', 'any', 30, 'data entry', 'company', '300000', '200000', '2015-12-06'),
(4, 'vishal@gmail.com', 'part time /full time jobs ', 'good communication skill , ', 'fresher', 'jobs in various fields . ', 'mumbai', '2015-12-30', 'graduate', 100, 'various', 'consultancy', '300000', '200000', '2015-12-17'),
(5, 'vishal@gmail.com', 'urgent requirement for java and php ', 'java, ajax, php, javascript, codeigniter,mysql', 'fresher', 'we are hiring urgent requirment for java and php programers . itrested candidate please apply as early as possible with your updated resume .', 'indore, bhopal, noida', '2016-01-08', 'graduation', 30, 'Information technology, web developer, programmer, web designer', 'consultancy', '200000', '75000', '2015-12-15'),
(6, 'avl@gmail.com', 'fsdsf', 'ms word, ', 'fresher', 'saf hfhj j tujry het egsfxgr tvbgg  ', 'surat, ', '2016-01-28', '12th', 20, 'data entry, ', '', '', '', '0000-00-00'),
(7, 'avl@gmail.com', 'asf ', 'ms word, ', '1 year', 'sdf ghtfe gdfv    df gdfgv ', 'agra', '2015-12-31', '12th', 20, 'data entry, ', 'company', '100000', '', '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `loc_id` int(11) NOT NULL,
  `loc_name` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`loc_id`, `loc_name`) VALUES
(1, 'indore'),
(2, 'agra'),
(3, 'bhopal'),
(4, 'gwalior'),
(5, 'chennai'),
(6, 'gujrat'),
(7, 'banglore'),
(8, 'new delhi'),
(9, 'mumbai'),
(10, 'jaipur'),
(11, 'ajmer'),
(12, 'surat'),
(13, 'hydrabad'),
(14, 'chandigarh'),
(15, 'kerela'),
(16, 'sikkim'),
(17, 'kanpur'),
(18, 'noida'),
(19, 'delhi'),
(20, 'dewas');

-- --------------------------------------------------------

--
-- Table structure for table `login_details`
--

CREATE TABLE IF NOT EXISTS `login_details` (
  `log_id` int(11) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_type` varchar(100) NOT NULL,
  `code` varchar(200) NOT NULL,
  `user_status` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login_details`
--

INSERT INTO `login_details` (`log_id`, `email`, `password`, `user_type`, `code`, `user_status`) VALUES
(1, 'abhi@gmail.com', '123', 'user', '', 'yes'),
(2, 'avl@gmail.com', '123', 'company', '', 'yes'),
(3, 'vishal@gmail.com', '123', 'consultancy', '', 'yes'),
(4, 'mahesh@gmail.com', '123', 'user', '', 'yes'),
(5, 'rupali@gmail.com', '123', 'user', '', 'yes'),
(11, 'hello@gmail.com', '123', 'user', '1254', 'yes'),
(12, 'hello@gmail.com', '123', 'user', '', 'no'),
(15, 'company@gmail.com', '123', 'company', '1905847527', 'yes'),
(16, 'aabd@gmail.com', '123', 'user', '', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `only_resume`
--

CREATE TABLE IF NOT EXISTS `only_resume` (
  `resume_id` int(11) NOT NULL,
  `resume` varchar(1000) NOT NULL,
  `pref_location` varchar(200) NOT NULL,
  `pref_field` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `only_resume`
--

INSERT INTO `only_resume` (`resume_id`, `resume`, `pref_location`, `pref_field`) VALUES
(1, 'User_dashboard7.docx', 'indore', 'Information technology'),
(2, 'Pages2.docx', 'indore', 'web developer'),
(3, 'User_dashboard9.docx', 'gwalior', 'Computer Applicaition');

-- --------------------------------------------------------

--
-- Table structure for table `payements`
--

CREATE TABLE IF NOT EXISTS `payements` (
  `pay_id` int(11) NOT NULL,
  `pay_date` date NOT NULL,
  `Exp_date` date NOT NULL,
  `pay_status` varchar(200) NOT NULL,
  `com_email` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `resume_count`
--

CREATE TABLE IF NOT EXISTS `resume_count` (
  `res_id` int(11) NOT NULL,
  `com_email` varchar(100) NOT NULL,
  `user_email` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `resume_count`
--

INSERT INTO `resume_count` (`res_id`, `com_email`, `user_email`) VALUES
(3, 'avl@gmail.com', 'rupali@gmail.com'),
(4, 'avl@gmail.com', 'mahesh@gmail.com'),
(5, 'avl@gmail.com', 'abhi@gmail.com'),
(6, 'avl@gmail.com', '1');

-- --------------------------------------------------------

--
-- Table structure for table `short_listed`
--

CREATE TABLE IF NOT EXISTS `short_listed` (
  `list_id` int(11) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `com_email` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE IF NOT EXISTS `skills` (
  `skill_id` int(11) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `skill_name` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skills`
--

INSERT INTO `skills` (`skill_id`, `user_email`, `skill_name`) VALUES
(44, 'abhi@gmail.com', 'adobe photoshop'),
(45, 'abhi@gmail.com', 'ajax'),
(46, 'abhi@gmail.com', 'codeigniter'),
(47, 'abhi@gmail.com', 'php');

-- --------------------------------------------------------

--
-- Table structure for table `skill_list`
--

CREATE TABLE IF NOT EXISTS `skill_list` (
  `skill_no` int(11) NOT NULL,
  `skill_name` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skill_list`
--

INSERT INTO `skill_list` (`skill_no`, `skill_name`) VALUES
(1, 'php'),
(2, 'javascript'),
(3, 'java'),
(4, 'android'),
(5, 'ajax'),
(6, 'c'),
(7, 'c++'),
(8, '.net'),
(9, 'codeigniter'),
(10, 'wordpress'),
(11, 'visual basic'),
(12, 'seo'),
(13, 'cce'),
(14, 'ms excel'),
(15, 'ms word'),
(16, 'adobe photoshop'),
(17, 'corel draw'),
(18, 'adobe pagemaker');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `user_address` varchar(500) NOT NULL,
  `user_contact` varchar(100) NOT NULL,
  `resume` varchar(500) NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `dob` date NOT NULL,
  `pref_field` varchar(100) NOT NULL,
  `pref_location` varchar(100) NOT NULL,
  `education` varchar(200) NOT NULL,
  `total_exp` varchar(200) NOT NULL,
  `industry` varchar(200) NOT NULL,
  `zip` varchar(100) NOT NULL,
  `city` varchar(200) NOT NULL,
  `state` varchar(200) NOT NULL,
  `country` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `gender`, `user_address`, `user_contact`, `resume`, `user_email`, `dob`, `pref_field`, `pref_location`, `education`, `total_exp`, `industry`, `zip`, `city`, `state`, `country`) VALUES
(2, 'Abhishek Vishwakarma', 'male', 'mp nagar indore ', '896554214', 'User_dashboard8.docx', 'abhi@gmail.com', '2015-12-10', 'Computer Applicaition', 'Bhopal', '', '', '', '', '', '', ''),
(3, 'mahesh yadav', 'male', '', '2147483647', 'Pages1.docx', 'mahesh@gmail.com', '0000-00-00', 'web designer', '', '', '', '', '', '', '', ''),
(4, 'rupali pal', 'female', '', '986564123', 'User_dashboard.docx', 'rupali@gmail.com', '0000-00-00', 'Computer Applicaition', '', '', '', '', '', '', '', ''),
(10, 'abc', '', '', '1111111111', 'User_dashboard3.docx', 'hello@gmail.com', '0000-00-00', '', '', 'graduate', '<1 year', 'Information technology', '452015', 'Indore', 'Madhya Pradesh', 'India'),
(11, 'abc', '', '', '1111111111', 'User_dashboard1.docx', 'hello@gmail.com', '0000-00-00', '', '', 'graduate', '<1 year', 'Information technology', '452015', 'Indore', 'Madhya Pradesh', 'India'),
(12, 'abc', '', '', '9856541023', 'User_dashboard.docx', 'aabd@gmail.com', '0000-00-00', '', '', 'graduate', '2 year', 'Information technology', '452015', 'Indore', 'Madhya Pradesh', 'India');

-- --------------------------------------------------------

--
-- Table structure for table `user_apply`
--

CREATE TABLE IF NOT EXISTS `user_apply` (
  `app_id` int(11) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `job_id` int(11) NOT NULL,
  `applydate` date NOT NULL,
  `curr_status` varchar(100) NOT NULL COMMENT 'apply/cancel/reject/select'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_apply`
--

INSERT INTO `user_apply` (`app_id`, `user_email`, `job_id`, `applydate`, `curr_status`) VALUES
(1, 'abhi@gmail.com', 3, '2015-12-01', 'reject'),
(2, 'abhi@gmail.com', 2, '2015-12-05', 'select'),
(5, 'abhi@gmail.com', 5, '0000-00-00', 'pending');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ads`
--
ALTER TABLE `ads`
  ADD PRIMARY KEY (`ad_id`);

--
-- Indexes for table `certificate`
--
ALTER TABLE `certificate`
  ADD PRIMARY KEY (`cer_id`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`com_id`);

--
-- Indexes for table `counter`
--
ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `education`
--
ALTER TABLE `education`
  ADD PRIMARY KEY (`edu_id`);

--
-- Indexes for table `experience`
--
ALTER TABLE `experience`
  ADD PRIMARY KEY (`exp_id`);

--
-- Indexes for table `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`fav_id`);

--
-- Indexes for table `fields`
--
ALTER TABLE `fields`
  ADD PRIMARY KEY (`field_id`);

--
-- Indexes for table `job_posted`
--
ALTER TABLE `job_posted`
  ADD PRIMARY KEY (`job_id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`loc_id`);

--
-- Indexes for table `login_details`
--
ALTER TABLE `login_details`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `only_resume`
--
ALTER TABLE `only_resume`
  ADD PRIMARY KEY (`resume_id`);

--
-- Indexes for table `payements`
--
ALTER TABLE `payements`
  ADD PRIMARY KEY (`pay_id`);

--
-- Indexes for table `resume_count`
--
ALTER TABLE `resume_count`
  ADD PRIMARY KEY (`res_id`);

--
-- Indexes for table `short_listed`
--
ALTER TABLE `short_listed`
  ADD PRIMARY KEY (`list_id`);

--
-- Indexes for table `skills`
--
ALTER TABLE `skills`
  ADD PRIMARY KEY (`skill_id`);

--
-- Indexes for table `skill_list`
--
ALTER TABLE `skill_list`
  ADD PRIMARY KEY (`skill_no`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_apply`
--
ALTER TABLE `user_apply`
  ADD PRIMARY KEY (`app_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ads`
--
ALTER TABLE `ads`
  MODIFY `ad_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `certificate`
--
ALTER TABLE `certificate`
  MODIFY `cer_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `com_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `counter`
--
ALTER TABLE `counter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `education`
--
ALTER TABLE `education`
  MODIFY `edu_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `experience`
--
ALTER TABLE `experience`
  MODIFY `exp_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `favourite`
--
ALTER TABLE `favourite`
  MODIFY `fav_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `fields`
--
ALTER TABLE `fields`
  MODIFY `field_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `job_posted`
--
ALTER TABLE `job_posted`
  MODIFY `job_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `loc_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `login_details`
--
ALTER TABLE `login_details`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `only_resume`
--
ALTER TABLE `only_resume`
  MODIFY `resume_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `payements`
--
ALTER TABLE `payements`
  MODIFY `pay_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `resume_count`
--
ALTER TABLE `resume_count`
  MODIFY `res_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `short_listed`
--
ALTER TABLE `short_listed`
  MODIFY `list_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `skills`
--
ALTER TABLE `skills`
  MODIFY `skill_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT for table `skill_list`
--
ALTER TABLE `skill_list`
  MODIFY `skill_no` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `user_apply`
--
ALTER TABLE `user_apply`
  MODIFY `app_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
