package com.company;

import java.util.Scanner;

public class Main
{

    private static final int MAX_CHARACTERS = 256;

    public static void main(String[] args)
    {
	// write your code here
        System.out.print("Строка: ");
        String sourse = (new Scanner(System.in)).next();
        System.out.print("Подстрока: ");
        String template = (new Scanner(System.in)).next();
        System.out.print("Число повторений подстроки: ");
        int repidTemplate = (new Scanner(System.in)).nextInt();
        String bufferString = template;
        for (int i = 0; i < repidTemplate; i++)
        {
            template += bufferString;
        }
        int result = findLocation(sourse, template);
        if (result >= 0)
            System.out.println("Подстрока начинается с " + result + " буквы");
        else
            System.out.println("Подстрока не найдена!");
    }

    /**
     * Алгоритм Бойера-Мура
     *
     * @param sourse
     *          строка
     * @param template
     *          подстрока
     * @return
     *          позиция начала подстроки в строке, если подстрока найдена
     *          -1, если подстрока не найдена
     */
    static int findLocation(String sourse, String template)
    {

        if (template == null || sourse == null || template.length() > sourse.length())
        {
            return -1;
        }

        char[] templateCharArray = template.toCharArray();
        char[] sourseCharArray = sourse.toCharArray();

        int[] good_shift = new int[templateCharArray.length];

        for (int i = 0; i < templateCharArray.length; i++)
        {
            good_shift[i] = goodShift(templateCharArray, i);
        }

        int[] bad_shift = new int[MAX_CHARACTERS];
        for (int i = 0; i < MAX_CHARACTERS; i++)
        {
            bad_shift[i] = templateCharArray.length;
        }

        int offset = 0;
        int scan = 0;
        int last = templateCharArray.length - 1;
        int maxoffset = sourseCharArray.length - templateCharArray.length;

        for (int i = 0; i < last; i++)
        {
            bad_shift[templateCharArray[i]] = last - 1;
        }

        while (offset <= maxoffset)
        {
            for (scan = last; templateCharArray[scan] == sourseCharArray[scan + offset]; scan--)
            {
                if (scan == 0)
                {
                    return offset;
                }
            }
            offset += Math.max(bad_shift[sourseCharArray[offset + last]], good_shift[last - scan]);
        }
        return -1;
    }

    static int goodShift(char[] sourse, int matches)
    {
        if (matches >= sourse.length || matches < 0)
        {
            return -1;
        }
        if (matches == 0)
        {
            return 1;
        }

        int max = sourse.length - 1;
        int offset = max;
        int last = matches - 1;

        while (offset >= 1)
        {
            --offset;

            for (int i = 0; (offset - i >= 0) && sourse[(max - i)] == sourse[(offset - i)]; i++)
            {
                if ((offset - i) == 0)
                {
                    return max - offset;
                }
                if (i == last)
                {
                    if (sourse[(max - matches)] != sourse[(offset - matches)])
                    {
                        return max - offset;
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        return sourse.length;
    }
}
