drop table if exists bids;
drop table if exists items;
drop table if exists users;
create table bids (bid_id bigint not null auto_increment, bid_created_at bigint, sum integer not null, item_id bigint, user_user_id bigint, primary key (bid_id));
create table items (id bigint not null auto_increment, buyer varchar(255), item_created_at bigint, description varchar(100), is_sellable bit, name varchar(100), photo_url varchar(100), purchase_price integer, starting_price integer, primary key (id));
create table users (user_id bigint not null auto_increment, account integer, password varchar(255), username varchar(100), primary key (user_id));
alter table bids add constraint FKg1mdb2uha9v6t2ujkvlmj3tuq foreign key (item_id) references items (id);
alter table bids add constraint FKr5ne1h7158yksdl1wru9kdct2 foreign key (user_user_id) references users (user_id);