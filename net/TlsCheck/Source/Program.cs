using System;
using System.IO;
using System.Net;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;

            using (var response = WebRequest.Create("https://tlstest.paypal.com/").GetResponse())
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                Console.WriteLine(streamReader.ReadToEnd());
            }
        }
    }
}
