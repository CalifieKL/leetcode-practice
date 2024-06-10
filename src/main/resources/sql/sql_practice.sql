--Customer Who Visited but Did Not Make Any Transactions
--Solution Original
select customer_id, count(visit_id) as count_no_trans from Visits
where visit_id not in (
    select distinct visit_id from Transactions
)
group by customer_id;
--Solution Optimal

--Rising Temperature
--Solution Original
select id from (
    select id, recordDate,temperature,
    lag(temperature, 1) over (order by recordDate, temperature) as prev_temp,
    lag(recordDate, 1) over (order by recordDate) as prev_date
    from Weather
)
where prev_temp < temperature and recordDate - prev_date = 1;
--NOTE: lag(expr[,offset][,default]) over ([query_partition_clause][order_by_clause])
-- [order_by_clause] seems to be necessary for getting correct results
--Solution Original 1
select WeatherOriginal.id as id from (
    select id, recordDate,temperature from Weather
) WeatherOriginal left join (
    select id, (recordDate+1) as tomorrow, temperature as prev_temp from Weather
) WeatherProcessed
on WeatherOriginal.recordDate = WeatherProcessed.tomorrow
where prev_temp < temperature;
--Solution Clean
select a.id from Weather a ,weather b
where a.recordDate =(b.recordDate +1)
and a.temperature>b.temperature

--Average Time of Process Per Machine
--Solution Original
select machine_id, round(avg(raw_processing_time),3) as processing_time from(
    select a.machine_id,  (b.timestamp-a.timestamp) as raw_processing_time from (
        (select machine_id, process_id, timestamp from activity where activity_type='start') a
        inner join (select machine_id, process_id, timestamp from activity where activity_type='end') b
        on a.machine_id=b.machine_id and a.process_id = b.process_id
    )
)group by machine_id;

--Students and Examinations
--Solution original
select a.student_id as student_id, a.student_name as student_name, a.subject_name as subject_name, count(b.subject_name) as attended_exams from(
    (select student_id, student_name, subject_name from Students, Subjects) a left join Examinations b
    on a.student_id=b.student_id and a.subject_name=b.subject_name
)
group by a.student_id, a.student_name, a.subject_name
order by a.student_id, a.student_name, a.subject_name;

--Managers with at Least 5 Direct Reports
--first original
select name from Employee where id in (select managerId from(
    select managerId, count(*) as num
    from Employee
    group by managerId)
where num > 4);
--second original
select Employee.name from (
    Employee left join (select managerId, count(*) as num from Employee group by managerId) a
    on Employee.id = a.managerId
)where num > 4;

--Confirmation Rate
--Solution original
select Signups.user_id as user_id, case when Rate.rate is null then 0 else Rate.rate end as confirmation_rate from(
    Signups left join(
        select a.user_id, case when confirmations is null then 0 else round(confirmations/allRequests,2) end as rate from
        (select user_id, count(*) as allRequests from Confirmations group by user_id)a
        left join
        (select user_id, count(*) as confirmations from Confirmations where action='confirmed' group by user_id)b
        on a.user_id = b.user_id
    ) Rate
    on Signups.user_id=Rate.user_id);
--if statement doesn't work for the rate selection:
--i.e.,if Rate.rate is null then 0 else Rate.rate end if as confirmation_rate
--will give error
--Solution optimal
select Signups.user_id, round(avg(case when Confirmations.action='confirmed' then 1 else 0 end), 2) as confirmation_rate from (Signups left outer join Confirmations on Signups.user_id = Confirmations.user_id)
group by Signups.user_id
--Note: avg() - confirmation_rate = #confirmation/#request=(1*#confirmation+0*#timeout)/(#confirmation+#timeout)

--Average Selling Price
--Solution Original
select product_id,
case when round(sum(units*price)/sum(units),2) is null then 0 else round(sum(units*price)/sum(units),2) end as average_price from (
    select p.product_id as product_id, p.price as price, u.units as units
    from Prices p left outer join UnitsSold u
    on p.product_id=u.product_id and (u.purchase_date between p.start_date and p.end_date)
)
group by product_id;
--Solution Cleaner (use coalesce)
select product_id,
coalesce(round(sum(units*price)/sum(units),2),0) as average_price from (
    select p.product_id as product_id, p.price as price, u.units as units
    from Prices p left outer join UnitsSold u
    on p.product_id=u.product_id and (u.purchase_date between p.start_date and p.end_date)
)
group by product_id;

--Percentage of Users Attended a Contest
--Solution Original
select contest_id, round(participants*100/total,2) as percentage from
(select contest_id, count(user_id) as participants from Register group by contest_id) r,
(select count(*)  as total from Users) t
order by percentage desc, contest_id
--Solution Cleaner
select contest_id, round(count(*)*100/(select count(*) from Users),2) as percentage
from Register
group by contest_id
order by percentage desc, contest_id;
--Note: select aggregate can be treated as constant

--Queries Quality and Percentage
--Solution Clean
select query_name,
round(sum(rating/position)/count(*),2) as quality,
round(100*sum(case when rating<3 then 1 else 0 end)/count(*),2) as poor_query_percentage
from Queries
where query_name is not null
group by query_name;
--Note: avg() can be used for the quality calculation

--Monthly Transactions I
--Solution Original
select month, country,
count(*) as trans_count,
sum(case when state='approved' then 1 else 0 end) as approved_count,
sum(amount) as trans_total_amount,
sum(case when state='approved' then amount else 0 end) as approved_total_amount
from (select to_char(trans_date,'yyyy-mm') as month, country, state, amount from Transactions)
group by month, country;
--Solution Clean
select to_char(trans_date,'yyyy-mm') as month,
country,
count(*) as trans_count,
sum(case when state='approved' then 1 else 0 end) as approved_count,
sum(amount) as trans_total_amount,
sum(case when state='approved' then amount else 0 end) as approved_total_amount
from Transactions
group by to_char(trans_date,'yyyy-mm'), country;
--Note: can group by function call result; plsql uses single quotation mark for strings

--Immediate Food Delivery II
--Solution Original
select
    round(100*sum(case when order_date=customer_pref_delivery_date then 1 else 0 end)/count(*),2) as  immediate_percentage
from
    (select customer_id, min(order_date) keep(dense_rank first order by order_date) as first_order
    from Delivery group by customer_id) fo
    left outer join
    (select customer_id, order_date, customer_pref_delivery_date from Delivery) d
    on fo.customer_id =d.customer_id and fo.first_order = d.order_date;
--Solution Clean
select round(100*avg(case when order_date=customer_pref_delivery_date then 1 else 0 end),2) as  immediate_percentage
from delivery
where (customer_id, order_date) in
(select customer_id, min(order_date) as first_order from Delivery group by customer_id);
--Note: dense_rank first/last order by clause is for sorting of aggregate on a different column
--Solution alternative without group by
select round(100*avg(case when order_date=customer_pref_delivery_date then 1 else 0 end),2) as  immediate_percentage
from delivery
where (customer_id, order_date) in
(select customer_id,
min(order_date) keep (dense_rank first order by order_date) over (partition by customer_id) as first_order
from Delivery);

--Game Play Analysis IV
--Solution Original
select round(case
    when count(distinct Activity.player_id)=0 then 0
    else count(distinct a.player_id)/count(distinct Activity.player_id) end,
    2) as fraction from
Activity cross join(
    select player_id from Activity where (player_id,event_date) in (
        select player_id, (min(event_date)
            keep (dense_rank first order by event_date)
            over (partition by player_id))+1
        from Activity)
)a;
--Note: this has a weird edge case when a.player_id is empty (error would be 0 divisor)
--Solution Clean
select round(
    sum(case
        when (select min(a1.event_date) from Activity a1 where a1.player_id = a.player_id)+1=a.event_date then 1
        else 0 end)/count(distinct a.player_id),2) as fraction
from Activity a;
--Note: select & reference to another table can be used in case clause

--User Activity for the Past 30 Days I
--Solution Original
select to_char(activity_date,'yyyy-mm-dd') as day, count(distinct user_id) as active_users from Activity
where activity_date+30> to_date('2019-07-27', 'yyyy-mm-dd') and activity_date <= to_date('2019-07-27', 'yyyy-mm-dd')
group by activity_date;
--Note: the use of to_char() and to_date()

--Classes More Than 5 Students
--Solution Original
select class from Courses group by class having count(*) >= 5;
--Note: having clause

--Customers who Bought All Products
--Solution without having
select p.customer_id from
(select customer_id, count(distinct product_key) as count from Customer
group by customer_id) p
where count=(select count(*) from Product);
--Solution with having
select customer_id from Customer
group by customer_id
having count(distinct product_key)=(select count(*) from Product)
--Note: having clause gets rid of nested query

--The Number of Employees Reporting to Each Manager
--Solution Original
select m.manager_id as employee_id, e.name, m.reports_count, m.average_age
from Employees e
right outer join
    (select reports_to as manager_id, count(*) as reports_count, round(avg(age)) as average_age
    from Employees
    where reports_to is not null
    group by reports_to) m
on e.employee_id=m.manager_id;
order by m.manager_id;
--Solution without Nested Query
select e1.employee_id ,
e1.name, count(*) as reports_count,
round(avg(e2.age)) as average_age from Employees e1 inner join Employees e2
on e1.employee_id=e2.reports_to
group by e1.employee_id, e1.name
order by e1.employee_id

--Product Price at A Given Date
--Solution Original
select
    distinct p.product_id, case when valid.price is null then 10 else valid.price end as price
from
    Products p
left outer join
    (select product_id,max(new_price) keep (dense_rank last order by change_date) as price from Products
    where change_date<=to_date('2019-08-16','yyyy-mm-dd')
    group by product_id) valid
on p.product_id=valid.product_id;
--Note: dense_rank first/last order by clause needs a group by clause
--Union approach
select
    product_id, first_value(new_price) over (partition by product_id order by change_date desc) as price
from Products
where change_date<=to_date('2019-08-16','yyyy-mm-dd')
union
select
    product_id, 10 as price
from Products
group by product_id
having min(change_date)>to_date('2019-08-16','yyyy-mm-dd');
--Note: aggregate function value as selecting condition argument needs to be paired with group by
--i.e. group by [column] having [some condition with aggregate function]

--Last Person to Fit in the Bus
--Solution half original
select
    distinct first_value(w.person_name) over (order by w.total_weight desc) as person_name
from
    (select turn, person_name, sum(weight) over (order by turn) as total_weight from Queue
    order by turn desc)w
where w.total_weight<=1000;
--Note: first_value returns duplicates (same number of copy as count(*) without distinct)
--Note: sum () over (order by) can be used without group by
--Solution Cleaner
select
    w.person_name as person_name
from
    (select turn, person_name, sum(weight) over (order by turn) as total_weight from Queue
    order by turn desc)w
where w.total_weight<=1000 and rownum=1;
--Note: rownum is the row number of the returned query result

--Movie Rating
--Solution Original
select uresult.u_result as results from(
    select
        distinct count(mr.rating) over (partition by mr.user_id) as rated, u.name as u_result
    from MovieRating mr left outer join Users u on mr.user_id=u.user_id
    order by rated desc, u.name
) uresult
where rownum=1
union all
select mresult.m_result as results from (
    select
        distinct avg(mr.rating) over (partition by mr.movie_id) as avg_rating, m.title as m_result
    from MovieRating mr left outer join Movies m on mr.movie_id=m.movie_id
    where mr.created_at<=to_date('2020-02-29','yyyy-mm-dd') and mr.created_at>= to_date('2020-02-01','yyyy-mm-dd')
    order by avg_rating desc, m.title
) mresult
where rownum=1;
--Note: union doesn't allow duplicates in result; union all allows duplicates
--Note: pay attention to the correct location to put the where rownum=1 clause (has to be after the ordering)

--Restaurant Growth
--Solution
with week_table as (
    select distinct a.visited_on as start_date, b.visited_on as end_date
    from Customer a inner join Customer b
    on a.visited_on=b.visited_on-6;
)

select to_char(w.start_date+6, 'yyyy-mm-dd') as visited_on,
    sum(c.amount) as amount, round(sum(c.amount)/7, 2) as average_amount
from week_table w, Customer c
where c.visited_on between w.start_date and w.end_date
group by w.start_date
order by w.start_date;

--Friend Requests II
--Solution convoluted
select id, num from(
    select case when r.id is null then a.id else r.id end as ID,
    ((case when requests is null then 0 else requests end)+(case when accepted is null then 0 else accepted end)) as num
    from
        (select requester_id as id, count(requester_id) as requests from RequestAccepted group by requester_id) r
        full outer join
        (select accepter_id as id, count(accepter_id) as accepted from RequestAccepted group by accepter_id) a
        on r.id=a.id
    order by num desc
) where rownum=1;
--Solution cleaner
select id, num from (
    select id, count(*) as num
    from (
        select requester_id as id from RequestAccepted
        union all
        select accepter_id as id from RequestAccepted
        ) group by id
        order by num desc
    )
where rownum=1;
--Note: union all to preserve duplicates

--Investments in 2016
--Solution original
with
    lt as (select lat, lon, count(*) as loc_count from Insurance group by (lat, lon)),
    it as (select tiv_2015, count(*) as iv15_count from Insurance group by tiv_2015)

select sum(tiv_2016) as tiv_2016
from Insurance
where
    tiv_2015 in (select tiv_2015 from it where iv15_count>1)
    and
    (lat,lon) not in (select lat, lon from lt where loc_count>1);
--Note: with [table name 1] as [select clause 1], [table name 2] as [select clause 2]
--separate views by comma, end with clause with white space

--Department Top 3 Salaries
--Solution Original
with
    s_rk as (
    select
        departmentId, salary, rank() over (partition by departmentId order by salary desc) as rk
    from (select distinct departmentId,salary from Employee)
    ),
    e_d as(
    select
        e.departmentId, d.name as Department, e.name, e.salary
    from Employee e
        left join Department d
        on e.departmentId=d.id
    )

select
    e_d.department as Department, e_d.name as Employee, e_d.salary as Salary
from e_d
    left join s_rk
    on e_d.departmentId=s_rk.departmentId and e_d.salary=s_rk.salary
where s_rk.rk<=3;
--Solution Cleaner
select department, employee, salary from(
    select
        d.name as department, e.name as employee, e.salary as salary,
        dense_rank() over (partition by e.departmentId order by salary desc) as rk
    from Employee e left join Department d on e.departmentId =d.id)
where rk in (1,2,3);
--Note: dense_rank() will produce consecutive ranking with tie; rank() will skip numbers after tie

--Fix Names in a Table
--Solution Original
select user_id, upper(substr(name,1,1))||lower(substr(name,2)) as name from Users order by user_id;
--Note: initcap() will capitilize first letter of every word (delimitered by white space or non-alphanumeric chars)

--Delete Duplicate Emails
--Solution Original
delete from Person
where id in (select id from (
    select id, dense_rank() over (partition by email order by id) as rk from Person
    ) where rk>1);
--Solution Cleaner
delete from Person
where id not in (select min(id) from Person group by email);

--Second Highest Salary
--Solution Collected 1
with highest_salary as (select max(salary) as max_s from Employee)
select max(salary) as SecondHighestSalary from Employee where salary < (select max_s from highest_salary);
--Solution Collected 2
select max(salary) as SecondHighestSalary from Employee where salary < (select max(salary) from Employee);
--Note: aggregate functions return null when arguments don't exists

--Group Sold Products by the Date
--Solution collected
select
    to_char(sell_date,'yyyy-mm-dd') as sell_date,
    count(*) as num_sold,
    listagg(product,',') within group (order by product) as products
from (select distinct * from Activities)
group by sell_date
order by sell_date;

--List the Products Ordered in a Period
--Solution Original
select distinct * from(
    select p.product_name, sum(o.raw_unit) over (partition by o.product_id) as unit from ((
        select product_id, unit as raw_unit, order_date from Orders
        where order_date >= to_date('2020-02-01','yyyy-mm-dd') and order_date <= to_date('202002-29','yyyy-mm-dd')
    ) o left join Products p on o.product_id=p.product_id)
)where unit>=100;
--Solution Cleaner
select p.product_name, sum(unit) as unit from Products p left join Orders o
on o.product_id=p.product_id
where to_char (order_date,'yyyy-mm')='2020-02'
group by p.product_name
having sum(unit)>=100;

--Find Users with Valid Emails
--Solution Collected 1
select user_id, name, mail from Users
where regexp_like(mail, '^[a-zA-Z][a-zA-Z0-9_.-]*@leetcode[.]com$');

--Top Travellers
--Solution Original
select * from(
    select
        u.name as name,  (case when travelled_distance is null then 0 else travelled_distance end) as travelled_distance
    from  Users u left join(
        select user_id,sum(distance) as travelled_distance from Rides group by user_id
    )r
    on r.user_id = u.id) res
order by res.travelled_distance desc, res.name;
--Solution Cleaner
select u.name as name, nvl(sum(r.distance),0) as travelled_distance
from users u left join rides r
on u.id=r.user_id
group by u.name, u.id
order by travelled_distance desc nulls last, name;

--Employees Earning More Than Their Managers
--Solution
select e.name as Employee from Employee e, Employee m
where e.managerid=m.id and e.salary>=m.salary;
--Note: the use of where when selecting from two tables

--Swap Salary
update salary
set sex = (case when sex='m' then 'f' else 'm' end);
--Note: no 'table' keyword in update statement

--Customers Who Never Order
select name as Customers from Customers where id not in (select distinct customerId from Orders);