# Próximos Passos - App Localizador Familiar

## ✅ O que você tem pronto agora

1. **Banco de dados** (Supabase)
   - 6 usuários criados
   - Tables de locations, emergency_sessions
   - RLS policies configuradas

2. **Frontend web** (family-locator.html)
   - Mapa interativo
   - Visualização de localizações
   - Cálculo de rotas (OSRM)
   - Botão de emergência

3. **Backend mobile** (arquivos Android)
   - LocationWorker (envia a cada 10 min)
   - FloatingButtonService (botão 🚨)
   - Encriptação AES-256
   - Suporte a emergência (1 min)

---

## 🔧 Como instalar o app Android

### Opção 1: Usar Android Studio (recomendado se for customizar)

1. Baixe **Android Studio** (developer.android.com)
2. Crie novo projeto Kotlin
3. Copie os 4 arquivos `.kt` fornecidos
4. Copie `activity_main.xml`
5. Substitua `AndroidManifest.xml`
6. Substitua `build.gradle.kts`
7. Vá em **Supabase** e copie os UUIDs:

```sql
-- SQL Editor no Supabase
SELECT phone, name, id FROM users WHERE family_group_id = '518f8b77-2fb4-498d-aed7-12a2df9933d2';
```

8. Cole os IDs em `Config.kt` no mapa:
```kotlin
val PHONE_TO_USER_ID = mapOf(
    "65999968208" to "SEU_UUID_AQUI",  // Marcel
    "6593338898" to "SEU_UUID_AQUI",   // Camila
    ...
)
```

9. Conecte seu celular via USB e rode: **Shift + F10**

### Opção 2: APK pronto (se não quiser programar)

Se preferir, posso gerar um APK pronto com suas credenciais. Você só:
1. Instala no celular
2. Abre e clica "Iniciar"
3. Pronto!

---

## 🧪 Testar antes de usar

**1. Abra o app no celular**
```
Clique: Iniciar Monitoramento
Autorize: Localização + Overlay (botão flutuante)
```

**2. Verifique no Supabase**
```
Dashboard → Table Editor → locations
Deve aparecer registros com sua localização
```

**3. Abra family-locator.html no navegador**
```
Você deve ver:
- Seu nome na lista
- Marcador no mapa com sua localização
```

**4. Teste emergência**
```
Segure o botão 🚨 por 3 segundos
Deve ficar vermelho vivo
App passa pra enviar a cada 1 minuto
```

---

## 🚨 Emergência: como funciona

```
User segura botão por 3s
    ↓
App muda pra vermelho (#ef4444)
    ↓
WorkManager agenda tarefa a cada 1 MINUTO
    ↓
Localização é enviada a cada 1 min (vs 10 min normal)
    ↓
Auto-desativa após 5 minutos
    ↓
Volta pra 10 minutos
```

---

## 📱 Instalar em todos os telefones

Você precisa:

1. **Para cada pessoa**, criar um app com seu `user_id`
   - Ou fazer 1 APK configurável (pedindo ID na primeira abertura)

2. **Compartilhar o APK**
   - Build → Build APK
   - Enviar por WhatsApp, email, etc

3. **Cada um instala**
   - Abre o app
   - Clica "Iniciar"
   - Pronto!

---

## 🔑 Dados importantes pra não perder

```
SUPABASE_URL = https://jqgpxnqexyoauzrgbyxa.supabase.co
SUPABASE_KEY = sb_publishable_1Mn10P2iSvuTTaakP_mP8g_4GiLc46O
FAMILY_GROUP_ID = 518f8b77-2fb4-498d-aed7-12a2df9933d2
```

---

## 🎯 O que falta

- [ ] Rodar Android Studio e criar projeto
- [ ] Copiar arquivos `.kt` e XML
- [ ] Copiar UUIDs do Supabase
- [ ] Rodar app no celular
- [ ] Testar localização
- [ ] Testar emergência
- [ ] Testar rota no mapa web
- [ ] Instalar nos outros telefones

---

## ❓ Se travar em algo

Avisa qual parte tá difícil que eu ajudo:

1. "Android Studio tá complicado" → Monto um APK pronto
2. "Não achei os UUIDs" → Te digo exatamente onde copiar
3. "Botão não aparece" → Debugger pra ver o que tá acontecendo
4. "Localização não envia" → Verificar logs

---

## 📞 Contato

Qualquer dúvida, chama. A infraestrutura tá pronta, é só colocar o app pra rodar.

**Próximo passo: Quer tentar no Android Studio ou prefere que eu monto o APK pronto?**
