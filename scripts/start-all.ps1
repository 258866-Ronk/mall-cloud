$ErrorActionPreference = 'Stop'
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) { throw 'Docker 未安装或不在 PATH 中，请先安装 Docker Desktop。' }
$root = Split-Path -Parent $PSScriptRoot
Push-Location $root
try { docker compose up -d --build; docker compose ps } finally { Pop-Location }
