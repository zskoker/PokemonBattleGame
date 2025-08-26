param
(
    [String] $FileName
)

$compress = @{
    Path = "./"
    CompressionLevel = "Fastest"
    DestinationPath = "./$($FileName).zip"
}

Compress-Archive @compress -Force