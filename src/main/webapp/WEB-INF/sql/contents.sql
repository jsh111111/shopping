CREATE TABLE IF NOT EXISTS `shop`.`category` (
  `cateno` INT NOT NULL AUTO_INCREMENT, -- 분류번호
  `catename` VARCHAR(50) NOT NULL,      -- 분류명
  `categrpno` INT NULL, -- 상위번호
  PRIMARY KEY (`cateno`),
  FOREIGN KEY (`categrpno`) REFERENCES category(`cateno`)
);

use shop;

insert into category(catename, categrpno)
values('Jean', null);
insert into category(catename, categrpno)
values('Bag', null);
insert into category(catename, categrpno)
values('Shoes', null);
        
select cateno, catename
from category
where categrpno is null;

CREATE TABLE IF NOT EXISTS `shop`.`contents` (
  `contentsno` INT NOT NULL AUTO_INCREMENT,
  `cateno` INT NULL,
  `pname` VARCHAR(50) NOT NULL,
  `price` INT NOT NULL,
  `filename` VARCHAR(100) NULL,
  `detail` LONGTEXT NOT NULL,
  `rdate` DATE NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`contentsno`),
  FOREIGN KEY (`cateno`) REFERENCES category (`cateno`)
  );
  
insert into contents(cateno, pname, price, filename, detail, rdate, stock)
values(1, 'Ripped Skinny Jeans', 50000, 'jeans2.jpg','찢어진 스키니 청바지 입니다.',sysdate(),10);
 
select contentsno, cateno, pname, price, filename, detail, stock 
from contents 
where contentsno = 1;
 
update contents
set pname ='Ripped Skinny Jeans2',
    price = 55000,
    filename = 'jeans1.jpg',
    detail = '청바지'
where contentsno = 1;
 
delete from contents
where contentsno = 1;
 
select contentsno, cateno,pname, price, filename, stock
from contents
order by contentsno desc
limit 0, 12;