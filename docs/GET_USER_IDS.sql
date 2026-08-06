-- Execute isto no Supabase SQL Editor para obter os UUIDs

SELECT phone, name, id FROM users WHERE family_group_id = '518f8b77-2fb4-498d-aed7-12a2df9933d2';

-- Resultado esperado:
-- phone         | name      | id (copie isto)
-- --------------|-----------|----------------------------------
-- 65999968208   | Marcel    | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
-- 6593338898    | Camila    | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
-- 6596852276    | Amábile   | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
-- 6598136447    | Amille    | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
-- 6593300-5784  | Noah      | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
-- 6598117-3233  | Edmara    | xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx

-- Depois, copie os IDs no arquivo Config.kt:
-- val PHONE_TO_USER_ID = mapOf(
--     "65999968208" to "AQUI_COLE_O_ID_DE_MARCEL",
--     ...
-- )
