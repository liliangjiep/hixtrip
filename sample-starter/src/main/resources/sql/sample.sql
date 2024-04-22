#todo 你的建表语句,包含索引
CREATE TABLE order_main (
    `id` VARCHAR(64) NOT NULL COMMENT '订单号',
    `user_id` VARCHAR(64) NOT NULL COMMENT '购买人',
    `sku_id` VARCHAR(64) NOT NULL COMMENT 'SkuId',
    `amount` INT NOT NULL COMMENT '购买数量',
    `money` DECIMAL(10, 2) NOT NULL COMMENT '购买金额',
    `pay_time` DATETIME NOT NULL COMMENT '购买时间',
    `pay_status` VARCHAR(10) NOT NULL COMMENT '支付状态',
    `del_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by` VARCHAR(64) NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '修改人',
    `update_time` DATETIME COMMENT '修改时间',
    INDEX idx_user_id (user_id),
    INDEX idx_sku_id (sku_id),
    INDEX idx_pay_status (pay_status)
) ENGINE=InnoDB COMMENT = '订单主表';

--订单子表
-- 创建订单子表，按月分表
CREATE TABLE order_sub (
  `id` VARCHAR(64) NOT NULL COMMENT '订单号',
  `user_id` VARCHAR(64) NOT NULL COMMENT '购买人',
  `sku_id` VARCHAR(64) NOT NULL COMMENT 'SkuId',
  `amount` INT NOT NULL COMMENT '购买数量',
  `money` DECIMAL(10, 2) NOT NULL COMMENT '购买金额',
  `pay_time` DATETIME NOT NULL COMMENT '购买时间',
  `pay_status` VARCHAR(10) NOT NULL COMMENT '支付状态',
  `del_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) COMMENT '修改人',
  `update_time` DATETIME COMMENT '修改时间',
   PRIMARY KEY (`id`),
   INDEX idx_sku_id (sku_id),
   INDEX idx_main_order_id (main_order_id),
   INDEX idx_create_time (create_time),
   INDEX idx_pay_status (pay_status)
) ENGINE=InnoDB COMMENT = '订单子表';

--1.分库键选择用户ID user_id，以确保每个用户的订单分布在不同的数据库中，从而提高查询性能和并发能力。

--2.分表键选择订单创建时间 create_time 的月份，以确保订单在不同的月份按照时间范围进行分表，避免单一表数据量过大的问题。

--3.这个设计可以满足买家和卖家频繁查询订单的需求，对于买家，订单实时性要求高，可以直接查询主表；订单号作为主键，确保查询性能。
--对user_id建立索引，以支持按购买人查询订单。
--由于买家主要关注订单的实时状态和信息，因此order_main表的设计应重点考虑查询性能。已经为user_id、sku_id和pay_status字段创建了索引，这有助于加快根据这些字段的查询速度。
--如果买家经常按时间范围查询订单，可以考虑添加create_time字段的索引，不需要则去除
--对于高峰期并发查询，可以通过增加缓存层（如Redis）来缓存热门查询结果，减少数据库压力。

--4.对于卖家，允许秒级延迟，可以根据具体需求查询主表或子表，子表按月分割，查询效率更高。
--订单子表按月分表的设计有助于管理和查询特定时间段的订单数据。对于卖家来说，他们可能更关心一段时间内的订单汇总或统计信息，因此这种设计是合适的。
--当卖家查询时，可以根据查询的时间范围确定需要查询的子表，从而避免全表扫描。
--异步更新与缓存：
--由于卖家查询允许秒级延迟，我们可以考虑使用异步更新机制来定期同步订单数据到缓存中。这样，当卖家发起查询时，可以直接从缓存中获取数据，减少对数据库的实时访问压力。
--缓存可以使用Redis等内存数据库来实现，通过定期刷新缓存数据来确保数据的相对实时性。
--如果卖家经常需要进行复杂的统计或汇总查询，可以考虑使用汇总表或物化视图来存储预先计算好的结果。这样，当卖家查询时，可以直接从汇总表或物化视图中获取数据，而不需要实时计算。
--缺点 它增加了数据一致性的维护成本和复杂性。在实际应用中，还需要考虑其他因素，如系统架构、数据量增长趋势、查询需求变化等，来选择合适的方案。
--分库分表中间件可以采用 Sharding-JDBC 来完成

--补充
--平台客服搜索客诉订单：
--对pay_time建立索引，以支持按时间范围查询订单。
--对于订单尾号和买家姓名搜索，可以考虑使用全文搜索功能（如MySQL的FULLTEXT索引），或者将搜索需求转至专门的搜索引擎（如Elasticsearch）。
--由于允许分钟级延迟，可以使用定时任务将数据同步到搜索引擎，减少实时写入压力。
--平台运营进行订单数据分析：
--对于数据分析，可以考虑定期将订单数据导出到数据仓库（如Hive）进行离线分析。
--在数据仓库中，可以使用更强大的计算能力和分析工具进行复杂的数据分析操作。
