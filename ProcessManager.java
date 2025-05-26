package ProcessManager;

import java.util.*;

public class ProcessManager {

    static class Process {
        static int nextId = 1;
        int id;
        String name;
        int burstTime; // prcessing time
        int priority;
        int remainingTime;

        public Process(String name, int burstTime, int priority) {
            this.id = nextId++;
            this.name = name;
            this.burstTime = burstTime;
            this.priority = priority;
            this.remainingTime = burstTime;
        }

        @Override
        public String toString() {
            return String.format("ID:%d Name:%s Burst:%d Priority:%d Remaining:%d",
                    id, name, burstTime, priority, remainingTime);
        }
    }

    static PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
    static Map<Integer, Process> runningProcesses = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Basit Süreç Yöneticisi (Process Manager)");
        System.out.println("Komutlar: add, list, run, stop <id>, exit");

        while (true) {
            System.out.print("cmd> ");
            String line = scanner.nextLine().trim();
            String[] parts = line.split(" ");

            if (parts.length == 0) continue;

            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "add":
                    // add <name> <burstTime> <priority>
                    if (parts.length < 4) {
                        System.out.println("Kullanım: add <name> <burstTime> <priority>");
                    } else {
                        String name = parts[1];
                        int burstTime, priority;
                        try {
                            burstTime = Integer.parseInt(parts[2]);
                            priority = Integer.parseInt(parts[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("burstTime ve priority tam sayı olmalı.");
                            break;
                        }
                        Process p = new Process(name, burstTime, priority);
                        readyQueue.offer(p);
                        System.out.println("Süreç eklendi: " + p);
                    }
                    break;

                case "list":
                    if (readyQueue.isEmpty()) {
                        System.out.println("Bekleyen süreç yok.");
                    } else {
                        System.out.println("Bekleyen süreçler:");
                        for (Process p : readyQueue) {
                            System.out.println("  " + p);
                        }
                    }
                    if (runningProcesses.isEmpty()) {
                        System.out.println("Çalışan süreç yok.");
                    } else {
                        System.out.println("Çalışan süreçler:");
                        runningProcesses.values().forEach(p -> System.out.println("  " + p));
                    }
                    break;

                case "run":
                    if (readyQueue.isEmpty()) {
                        System.out.println("Çalıştırılacak süreç yok.");
                    } else {
                        Process p = readyQueue.poll();
                        System.out.println("Süreç çalıştırılıyor: " + p);
                        // Simulate: decrease processing time, then stop or end
                        runningProcesses.put(p.id, p);
                    }
                    break;

                case "stop":
                    if (parts.length < 2) {
                        System.out.println("Kullanım: stop <process_id>");
                    } else {
                        try {
                            int id = Integer.parseInt(parts[1]);
                            Process p = runningProcesses.remove(id);
                            if (p == null) {
                                System.out.println("Bu ID ile çalışan süreç bulunamadı.");
                            } else {
                                System.out.println("Süreç durduruldu ve kaldırıldı: " + p);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("ID tam sayı olmalı.");
                        }
                    }
                    break;

                case "exit":
                    System.out.println("Çıkış yapılıyor.");
                    return;

                default:
                    System.out.println("Bilinmeyen komut.");
            }
        }
        
    }
}
