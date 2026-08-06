# FamiliApp 🗺️

App de localização familiar em tempo real com suporte a emergências.

## ✨ Recursos

- ✅ **Localização automática** a cada 10 minutos (background)
- ✅ **Modo emergência** - hold 3s no botão para 1 minuto (auto-desativa após 5 min)
- ✅ **Encriptação AES-256-GCM** em repouso
- ✅ **Mapa interativo** com Leaflet.js
- ✅ **Cálculo de rotas** via OSRM (open source)
- ✅ **Mínimo consumo de bateria** com WorkManager

## 🏗️ Arquitetura

```
┌─────────────────────┐
│   Android App       │
│   (WorkManager)     │
└──────────┬──────────┘
           │ HTTPS
           ↓
┌─────────────────────┐
│  Supabase           │
│  (PostgreSQL)       │
└──────────┬──────────┘
           │
      ┌────┴────┐
      ↓         ↓
   Frontend   Analytics
   (Web)
```

## 🛠️ Stack Técnico

| Componente | Tecnologia |
|-----------|-----------|
| Backend | Supabase + PostgreSQL |
| Mobile | Android (Kotlin) + WorkManager |
| Frontend | HTML + Leaflet.js |
| Encriptação | AES-256-GCM |
| Autenticação | JWT + Supabase Auth |
| Rotas | OSRM (open source) |

## 📱 Requisitos

- **Android**: 7.0+ (API 24)
- **Permissões**: Localização, Overlay, Notificações
- **Conexão**: WiFi ou dados móveis

## 🚀 Quickstart

### 1. Obter credenciais Supabase

Copie para `Config.kt`:

```kotlin
const val SUPABASE_URL = "https://seu-projeto.supabase.co"
const val SUPABASE_KEY = "sua-chave-publica"
const val FAMILY_GROUP_ID = "seu-family-group-id"
```

### 2. Copiar UUIDs dos usuários

Execute no Supabase SQL Editor:

```sql
SELECT phone, name, id FROM users 
WHERE family_group_id = '518f8b77-2fb4-498d-aed7-12a2df9933d2';
```

Copie em `Config.kt`:

```kotlin
val PHONE_TO_USER_ID = mapOf(
    "65999968208" to "uuid-marcel",
    "6593338898" to "uuid-camila",
    // ... etc
)
```

### 3. Build no Android Studio

```bash
cd android
./gradlew assembleDebug
```

### 4. Instalar no celular

```bash
adb install app/debug/app-debug.apk
```

### 5. Abrir frontend web

Abra `web/family-locator.html` no navegador.

## 📋 Estrutura do projeto

```
FamiliApp/
├── android/
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── kotlin/com/silva/familylocator/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── LocationWorker.kt
│   │   │   │   ├── FloatingButtonService.kt
│   │   │   │   ├── EncryptionUtil.kt
│   │   │   │   └── Config.kt
│   │   │   ├── res/layout/
│   │   │   │   └── activity_main.xml
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
│
├── web/
│   └── family-locator.html
│
├── docs/
│   ├── SETUP_ANDROID.md
│   ├── PROXIMO_PASSO.md
│   └── GET_USER_IDS.sql
│
└── README.md
```

## 🔐 Segurança

- **Encriptação**: AES-256-GCM em repouso
- **Transporte**: HTTPS + HSTS obrigatório
- **Autorização**: RLS policies no Supabase
- **Auditoria**: Cada acesso registrado em audit_log
- **Retenção**: Histórico automático deletado após 48h

## 👥 Usuários padrão

| Nome | Telefone |
|------|----------|
| Marcel | 65 9999-68208 |
| Camila | 65 9933-8898 |
| Amábile | 65 9685-2276 |
| Amille | 65 9813-6447 |
| Noah | 65 9330-05784 |
| Edmara | 65 9811-73233 |

## 📖 Documentação

- [Setup Android Studio](docs/SETUP_ANDROID.md)
- [Próximos passos](docs/PROXIMO_PASSO.md)
- [Query SQL para UUIDs](docs/GET_USER_IDS.sql)

## 🧪 Testes

### 1. Verificar localização no Supabase

```sql
SELECT * FROM locations 
ORDER BY created_at DESC 
LIMIT 10;
```

### 2. Testar emergência

- Segure o botão 🚨 na tela do app por 3 segundos
- Deve ficar vermelho vivo
- Verifique que `emergency = true` no banco

### 3. Testar rota

- Abra `web/family-locator.html`
- Selecione uma pessoa
- Clique em "Ir ao encontro de"
- Deve desenhar rota no mapa

## 🐛 Troubleshooting

**"Permissão negada"**
- Vá em Settings > Apps > FamiliApp > Permissions
- Ative: Location, Overlay, Notifications

**"Sem marcadores no mapa"**
- Verifique internet no celular
- Confira credenciais Supabase em `Config.kt`
- Veja se app foi iniciado

**"Botão flutuante não aparece"**
- Vá em Settings > Apps > Special access > Display over other apps
- Ative para FamiliApp

## 📊 Performance

| Métrica | Valor |
|---------|-------|
| Tamanho APK | ~25 MB |
| Uso de bateria (10 min) | +2–5% por dia |
| Uso de bateria (emergência) | +8–10% por 5 min |
| Tamanho dados/dia | ~1.4 KB por pessoa |

## 📄 Licença

MIT

## ✉️ Contato

Marcel Fernandes da Silva  
[GitHub](https://github.com/Xskysilva)

---

**Última atualização**: Agosto 2026  
**Versão**: 1.0
 
 
 
