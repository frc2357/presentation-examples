# 2357 Presentation Examples

Welcome to the **Presentation Examples** repository maintained by **FRC Team 2357 "System Meltdown"**. 

This repository contains example code, sample implementations, and demonstration projects accompanying Team 2357's training presentations on **WPILib 2027** and the **2027 FRC Control System**.

---

## Purpose

The purpose of this repository is to provide practical Java code examples that illustrate key concepts introduced in Team 2357's presentations. It is designed to assist FIRST Robotics Competition (FRC) teams in preparing for the changes and features introduced in the WPILib 2027 ecosystem, including new control architectures, OpModes, Commands v3, hardware configurations, and common software debugging techniques.

---

## Repository Contents

* **`2027xrp/`**: Implementation examples demonstrating robot control for the XRP platform using WPILib 2027 OpModes, `PeriodicOpMode`, and Commands v3 structures.
* **`OOPPrinciples/`**: Java examples illustrating foundational Object-Oriented Programming (OOP) concepts—such as Classes, Objects, Abstraction, Encapsulation, Inheritance, and Polymorphism—applied in robot programming contexts.
* **Common Error Demos**: Sample code blocks highlighting common robot programming pitfalls (such as Null Pointer Exceptions, Divide by Zero, Loop Overruns exceeding the 20ms periodic cycle, and blocking calls like `Thread.sleep()`) along with correct implementation patterns.

---

## Accompanying Presentation Slides

This repository directly pairs with the following presentations:

1. **[WPILib 2027 Fundamentals](https://docs.google.com/presentation/d/1B3gQlaUz8r_A6dOcYw-zERAcZ6aVUIn-jm_BZ4l1RSs/edit?slide=id.g3f8b0bcd8e3_0_156#slide=id.g3f8b0bcd8e3_0_156)**
   * *Topics Covered:* Object-Oriented Programming principles, SystemCore hardware overview, 2027 DriverStation features, `OpModeRobot` architecture, Commands v3 and coroutine execution, component/composite mechanisms, and the Telemetry/Tunables API.
2. **[Common Robot Programming Errors - WPILib 2027](https://docs.google.com/presentation/d/1eh-4UUrfWlj9mzfmbIXXVLKRLdSrHYMlKKXtG0EKwkI/edit?slide=id.p#slide=id.p)**
   * *Topics Covered:* Identifying and resolving common bugs in FRC code, understanding execution timing, avoiding thread-blocking code, proper team number setup, auto-pathing initial pose setup, and best practices for pre-deploy verification (code reviews, build checks, and simulation).