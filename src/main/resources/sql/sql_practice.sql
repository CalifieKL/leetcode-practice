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