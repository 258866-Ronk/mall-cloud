param([switch]$SkipTests)
$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$mavenVersion = '3.9.9'
$localMaven = Join-Path $root "work\tools\apache-maven-$mavenVersion\bin\mvn.cmd"
$systemMaven = Get-Command mvn -ErrorAction SilentlyContinue
if ($systemMaven) {
    $maven = $systemMaven.Source
} else {
    if (-not (Test-Path -LiteralPath $localMaven)) {
        $toolRoot = Join-Path $root 'work\tools'
        New-Item -ItemType Directory -Force -Path $toolRoot | Out-Null
        $zip = Join-Path $toolRoot "apache-maven-$mavenVersion-bin.zip"
        $url = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$mavenVersion/apache-maven-$mavenVersion-bin.zip"
        Write-Host "Downloading Maven $mavenVersion..."
        if (Get-Command curl.exe -ErrorAction SilentlyContinue) { & curl.exe -fL --retry 3 -o $zip $url } else { Invoke-WebRequest -Uri $url -OutFile $zip }
        Expand-Archive -LiteralPath $zip -DestinationPath $toolRoot -Force
    }
    $maven = $localMaven
}
$arguments = @('-ntp', 'clean', 'verify')
if ($SkipTests) { $arguments += '-DskipTests' }
Push-Location $root
try { & $maven @arguments; if ($LASTEXITCODE -ne 0) { throw "Maven build failed with exit code $LASTEXITCODE" } } finally { Pop-Location }
