-- Apr 23, 2013 8:44:22 PM COT
-- IDEMPIERE-570 Doc_Production - production posting problems
UPDATE AD_Column SET IsAllowCopy='N',Updated=TO_DATE('2013-04-23 20:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3609
;

-- Apr 23, 2013 8:44:35 PM COT
UPDATE AD_Column SET IsAllowCopy='N',Updated=TO_DATE('2013-04-23 20:44:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6537
;

-- Apr 23, 2013 8:44:57 PM COT
UPDATE AD_Column SET IsAllowCopy='N',Updated=TO_DATE('2013-04-23 20:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=59961
;

-- Apr 23, 2013 8:45:05 PM COT
UPDATE AD_Column SET IsAllowCopy='N',Updated=TO_DATE('2013-04-23 20:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4752
;

-- Apr 23, 2013 9:08:12 PM COT
UPDATE AD_Table SET AccessLevel='1',Updated=TO_DATE('2013-04-23 21:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=325
;

-- Apr 23, 2013 9:08:21 PM COT
UPDATE AD_Table SET AccessLevel='1',Updated=TO_DATE('2013-04-23 21:08:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=326
;

SELECT register_migration_script('201304232046_IDEMPIERE-570.sql') FROM dual
;

