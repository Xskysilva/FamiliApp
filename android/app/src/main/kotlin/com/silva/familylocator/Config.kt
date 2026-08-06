package com.silva.familylocator

object Config {
    // Supabase
    const val SUPABASE_URL = "https://jqgpxnqexyoauzrgbyxa.supabase.co"
    const val SUPABASE_KEY = "sb_publishable_1Mn10P2iSvuTTaakP_mP8g_4GiLc46O"
    
    // Família
    const val FAMILY_GROUP_ID = "518f8b77-2fb4-498d-aed7-12a2df9933d2"
    
    // Mapeamento de telefone para UUID
    // Copie os UUIDs do Supabase (Dashboard → Table Editor → users → column id)
    val PHONE_TO_USER_ID = mapOf(
        "65999968208" to "ID_MARCEL",      // Alterar para UUID real
        "6593338898" to "ID_CAMILA",       // Alterar para UUID real
        "6596852276" to "ID_AMABILE",      // Alterar para UUID real
        "6598136447" to "ID_AMILLE",       // Alterar para UUID real
        "6593300-5784" to "ID_NOAH",       // Alterar para UUID real
        "6598117-3233" to "ID_EDMARA"      // Alterar para UUID real
    )
    
    // Encriptação
    const val ENCRYPTION_KEY = "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"
}
