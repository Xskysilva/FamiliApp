# Setup App Android - Localizador Familiar

## 📋 Arquivos do projeto

```
app/src/main/
├── kotlin/com/silva/familylocator/
│   ├── MainActivity.kt
│   ├── LocationWorker.kt
│   ├── FloatingButtonService.kt
│   └── EncryptionUtil.kt
├── res/
│   └── layout/activity_main.xml
└── AndroidManifest.xml

build.gradle.kts (no root)
```

---

## 🚀 Passo-a-passo

### 1. Criar novo projeto Android Studio

```
File → New → New Android Project
Language: Kotlin
Minimum SDK: 24 (Android 7.0)
```

### 2. Copiar arquivos Kotlin

Coloque os 4 arquivos `.kt` em:
```
app/src/main/kotlin/com/silva/familylocator/
```

### 3. Layout XML

Crie a pasta `layout`:
```
app/src/main/res/layout/
```

Cole `activity_main.xml` ali.

### 4. AndroidManifest.xml

Substitua o manifesto padrão pelo fornecido.

### 5. build.gradle.kts

Substitua as dependencies pelo arquivo fornecido.

### 6. Configurar credenciais Supabase

No `MainActivity.kt`, procure por:

```kotlin
// TODO: Adicionar credenciais
val SUPABASE_URL = "https://seu-projeto.supabase.co"
val SUPABASE_KEY = "sua_chave_publica"
val USER_ID = "seu_user_id_aqui"  // UUID do usuário (ex: Marcel)
val FAMILY_GROUP_ID = "518f8b77-2fb4-498d-aed7-12a2df9933d2"
```

Substitua com seus dados.

### 7. Sync Gradle

```
File → Sync Now
```

Aguarde o download de dependências.

### 8. Rodar no celular ou emulador

```
Shift + F10 (ou Run → Run App)
```

---

## ⚙️ Permissões

O app pede automaticamente:
- ✅ Localização (precisa)
- ✅ Notificações (opcional)
- ✅ Overlay (botão flutuante)

---

## 🎯 Como usar

**1. Abrir app**
- Clique em "▶️ Iniciar Monitoramento"
- Autorize permissões

**2. Localização automática**
- App envia localização a cada 10 min (background)
- Você não precisa fazer nada
- Botão flutuante 🚨 fica sempre visível

**3. Emergência**
- **Segure o botão 🚨 por 3 segundos**
- Muda pra vermelho vivo
- Localização passa pra a cada 1 minuto
- Auto-desativa após 5 minutos

**4. Ver no mapa web**
- Abra `family-locator.html` no navegador
- Verá marcadores de todos no mapa

---

## 🔑 Dados necessários

Para cada telefone, crie um entry no `MainActivity.kt`:

```kotlin
// Marcel
val USER_ID = "550e8400-e29b-41d4-a716-446655440001"

// Camila
val USER_ID = "550e8400-e29b-41d4-a716-446655440002"

// ... etc
```

**Como obter o UUID?** No Supabase:
```
Dashboard → Table Editor → users
```

Copie o `id` de cada pessoa.

---

## 🔋 Bateria

- **10 min**: +2–5% por dia
- **1 min (emergência)**: +8–10% por 5 min
- Muito eficiente comparado a GPS contínuo

---

## 🐛 Troubleshooting

**"Permissão de localização negada"**
- Vá em Configurações > Apps > Localizador > Permissões
- Ative "Localização"

**"Botão flutuante não aparece"**
- Vá em Configurações > Apps > Permissões especiais > Exibir sobre outros apps
- Ative para "Localizador Familiar"

**"Não envia localização"**
- Verifique internet (WiFi ou dados móveis)
- Verifique se as credenciais Supabase estão corretas
- Veja Logcat: `android.util.Log.e("LocationWorker", ...)`

---

## 📱 Build APK para distribuir

```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

Gera arquivo `.apk` para instalar nos telefones da família.

---

**Dúvidas?** Chama aí!
