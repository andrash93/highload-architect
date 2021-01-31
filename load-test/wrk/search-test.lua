math.randomseed(os.time())

request = function()
    local array = {
        "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р",
        "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
    }
    local queryStrFirstName = array[math.random(32)]
    local queryStrLastName = array[math.random(32)]
    local url_path = "/api/account/search?firstName=" .. encodeURI(queryStrFirstName) .. "&lastName=" .. encodeURI(queryStrLastName)
    wrk.headers["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTYxMzEzNTgxNSwiaWF0IjoxNjExOTM1ODE1LCJqdGkiOiIxMTkxNjMyIn0.uK7E8dWo-dJfLMjiWgqZDOSWCWkCAK0VfApreNZXibI"
    return wrk.format("GET", url_path)
end

function encodeURI(str)
    if (str) then
        str = string.gsub (str, "\n", "\r\n")
        str = string.gsub (str, "([^%w ])",
            function (c) return string.format ("%%%02X", string.byte(c)) end)
        str = string.gsub (str, " ", "+")
    end
    return str
end