-- IDEMPIERE-6338
SELECT register_migration_script('202412061426_IDEMPIERE-6338.sql') FROM dual;

-- Dec 6, 2024, 2:26:58 PM CET
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-12-06 14:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=10 WHERE AD_Column_ID=214462
;

-- Dec 6, 2024, 2:27:05 PM CET
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20, IsUpdateable='N',Updated=TO_TIMESTAMP('2024-12-06 14:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=10 WHERE AD_Column_ID=214450
;

