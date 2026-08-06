# GitHub Actions - Build Automático de APK

## 🎯 O que vai acontecer

Toda vez que você fizer `git push`, o GitHub vai:

1. ✅ Clonar seu código
2. ✅ Instalar Java + Android SDK
3. ✅ Compilar o APK
4. ✅ Fazer upload para **Releases**

Você pode baixar o APK pronto em minutos.

---

## 📝 Passos

### 1. Adicione o workflow ao seu repositório

Copie o arquivo `.github/workflows/build.yml` ao seu projeto:

```
FamiliApp/
├── .github/
│   └── workflows/
│       └── build.yml
├── android/
└── ...
```

### 2. Faça commit

```bash
git add .github/workflows/build.yml
git commit -m "ci: Adicionar GitHub Actions para build automático"
git push
```

### 3. Aguarde a compilação

- Vá em **GitHub** → seu repositório
- Clique na aba **Actions**
- Você vai ver o workflow rodando
- Aguarde terminar (leva ~5-10 min na primeira vez)

### 4. Baixar o APK

Quando terminar:
- Clique na aba **Releases**
- Baixe o arquivo `app-debug.apk`

---

## 📥 Instalar no celular

### Opção A: Via USB (mais seguro)

```bash
# Conecte o celular via USB
adb install app-debug.apk
```

### Opção B: Copiar e tocar

1. Copie `app-debug.apk` para o celular (USB ou WhatsApp)
2. Abra o arquivo no celular
3. Toque para instalar

---

## 🔄 Próximas vezes

Toda vez que você fizer `git push`, o APK é compilado automaticamente.

```bash
# Faça uma mudança
echo "// comentário" >> android/app/src/main/...

# Commit e push
git add .
git commit -m "feature: adicionar algo"
git push

# GitHub compila automaticamente!
# Você apenas baixa em Releases
```

---

## 🧪 Testar agora

```bash
# Faça um pequeno commit (tipo comentário)
echo "# Pronto para build" >> README.md

git add README.md
git commit -m "test: trigger build"
git push

# Aguarde ~5 min
# Vá em: https://github.com/Xskysilva/FamiliApp/releases
# Baixe o APK
```

---

## ⚙️ Customizar (opcional)

Se quiser mudar algo no `build.yml`:

```yaml
# Mudar branch que compila:
on:
  push:
    branches: [ main ]  # Aqui

# Mudar tipo de build:
./gradlew assembleDebug  # Debug (mais rápido)
./gradlew assembleRelease # Release (produção)
```

---

## 🐛 Troubleshooting

**"Build falhando"**
- Verifique os **logs** em Actions
- Olhe se `Config.kt` tá correto

**"APK não aparece"**
- Pode demorar ~10 min na primeira vez
- Recarregue a página

**"Erro de permissões"**
- GitHub pode pedir autorização
- Clique em "Authorize"

---

## ✅ Checklist

- [ ] Arquivo `.github/workflows/build.yml` criado
- [ ] Fazer commit: `git push`
- [ ] Aguardar ~5-10 min
- [ ] Verificar em **Actions** se compilou
- [ ] Baixar APK em **Releases**
- [ ] Instalar no celular
- [ ] Testar app (localização + emergência)

---

**Próximo passo:** Faça um commit (mesmo que pequeno) pra trigger o build automático.

```bash
git add .
git commit -m "ci: Adicionar GitHub Actions para build automático"
git push
```

Depois abra: https://github.com/Xskysilva/FamiliApp/actions
