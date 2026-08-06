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
    "65999968208" to "56067289-7d85-4d65-8219-37400ead8722",  // Marcel
    "6593338898" to "49505b1c-7ae6-4b0d-933c-727adf60aa50",   // Camila
    "6596852276" to "7259bd38-69db-4f1b-b724-bb2668ebfb1d",   // Amábile
    "6598136447" to "d6e70ab6-be80-43b7-834b-625c082ffd9c",   // Amille
    "6593300-5784" to "f7acc93a-5a7d-4d64-abd0-59687ecb28bd", // Noah
    "6598117-3233" to "2c1d0609-9086-4fcc-9587-09d6aa20018f"  // Edmara
)
    )
    
    // Encriptação
    const val ENCRYPTION_KEY = "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"
}
