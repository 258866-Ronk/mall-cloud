$ErrorActionPreference = 'Stop'
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) { throw 'Docker 未安装或不在 PATH 中。' }
$root = Split-Path -Parent $PSScriptRoot
Push-Location $root
try { docker compose -f docker-compose.infra.yml down } finally { Pop-Location }
